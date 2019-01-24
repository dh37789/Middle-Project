package home.community.controller.sikdan;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import vo.Meal_chartVO;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.spi.CalendarDataProvider;

import home.community.service.IFreeBoardService;


public class FullCalendarView {
	private IFreeBoardService service;
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    
    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     * @throws RemoteException 
     * @throws NotBoundException 
     */
    public FullCalendarView(YearMonth yearMonth) throws RemoteException, NotBoundException {
    	Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (IFreeBoardService) reg.lookup("freeboard");
		currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(800, 600);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(300,200);
                calendar.add(ap,j,i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("일요일"), new Text("월요일"), new Text("화요일"),
                                        new Text("수요일"), new Text("목요일"), new Text("금요일"),
                                        new Text("토요일") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);
        
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
    	
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.getChildren().clear();
            ap.setDate(calendarDate);
            String d1 = calendarDate+"";
            Meal_chartVO vo = null;
			try {
				vo = service.getSikdan(d1);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
            if (vo != null) {
//            	Text t = new Text("\n___________________");
//            	TextFlow tf1 = new TextFlow(t);
//            	ap.getChildren().add(tf1);
            	
        		if((d1).equals(vo.getMlc_id())) {
        			Text text = new Text("\n\n"+vo.getMlc_ml());
        			TextFlow tf = new TextFlow(text);
        			ap.getChildren().add(tf);
        		}
			}
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
//        for (int i = 0; i < allCalendarDays.size(); i++) {
//        	allCalendarDays.get(i).getChildren().clear();
//		}
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
//        for (int i = 0; i < allCalendarDays.size(); i++) {
//        	allCalendarDays.get(i).getChildren().clear();
//		}
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}
