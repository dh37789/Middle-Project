package home.community.controller.sikdan;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.rmi.RemoteException;
import java.time.LocalDate;

import home.community.service.IFreeBoardService;


/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private IFreeBoardService service;
    
    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
//    Image img = new Image(getClass().getResourceAsStream("ticket.png"));
//    ImageView imgview = new ImageView();
    
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
//        imgview.setImage(img);
        this.setOnMouseClicked(e ->
//        	this.getChildren().addAll(imgview)
        {
        	
        	
        }
        );
        
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
