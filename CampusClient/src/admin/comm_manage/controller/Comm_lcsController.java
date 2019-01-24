package admin.comm_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import admin.comm_manage.service.IComm_manageService;
import home.community.service.ICommunityService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import vo.AdminVO;
import vo.LcsVO;

public class Comm_lcsController {
	private IComm_manageService service;
	private ICommunityService service1;
	
	private int lcsSize;
	private int rowPerPage = 15;
	private List<LcsVO> numList;
	private ObservableList<LcsVO> Data;
	List<LcsVO> list = new ArrayList<>();
	

    @FXML
    private Pagination page;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<LcsVO> tableLcs;

    @FXML
    private TableColumn<?, ?> tcNo;

    @FXML
    private TableColumn<?, ?> tcTitle;

    @FXML
    private TableColumn<?, ?> tcDate;
    
    @FXML
    private TableColumn<?, ?> tcAdmin;

    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnExit;
    
    private List<LcsVO> listall;
    
    @FXML
    private TextField tf_search;
    
    /**
     * 리스트검색
     */
    @FXML
    void search(KeyEvent event) {
    	List<LcsVO> list = null;
   	   String str ="%"+tf_search.getText().trim()+"%";
   	   try {
  			list = service1.lcsSearch(str);
  			if("".equals(str)) {
  				table(listall);
  			}
  			else {
  				table(list);
  			}
  		} catch (RemoteException e) {
  			e.printStackTrace();
  		}
    }
    
    void table(List<LcsVO> vo) {
    	ObservableList<LcsVO> ob;
    	ob = FXCollections.observableArrayList(vo);
        tableLcs.setItems(ob);
        
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("lcs_nm"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("lcs_tdt"));
    }

    private AdminVO avo;
    public void setAvo(AdminVO avo) {
    	this.avo = avo;
    }

//    // 나가기 버튼 누르면 화면 비우기
//    @FXML
//    void Exit(ActionEvent event) throws IOException {
//    	pane.getChildren().clear();
//    }
    
    // 추가 버튼 누르면 글 작성 화면으로 이동하는 메서드
    @FXML
    void add(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs_add.fxml"));
    	Parent root = loader.load();
    	
    	pane.getChildren().setAll(root);
    }
    
    ObservableList<LcsVO> data;
    
    // 목록
    @FXML
    void showContents(MouseEvent event) throws IOException {
    	LcsVO lvo = tableLcs.getSelectionModel().getSelectedItem();
    	if(event.getClickCount()==2&&!tableLcs.getSelectionModel().isEmpty()) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs_view2.fxml"));
    		Parent root = loader.load();
    		
    		Comm_lcs_viewController lcsView = loader.getController();
    		lcsView.setAvo(avo);
    		lcsView.setLcvo(lvo);
    		lcsView.viewBoard();
    		
    		pane.getChildren().setAll(root);
    	}
    }
    

    @FXML
    void initialize() throws RemoteException, NotBoundException {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_lcs.fxml'.";
    	assert tableLcs != null : "fx:id=\"tableLcs\" was not injected: check your FXML file 'comm_lcs.fxml'.";
        assert tcNo != null : "fx:id=\"tcNo\" was not injected: check your FXML file 'comm_lcs.fxml'.";
        assert tcTitle != null : "fx:id=\"tcTitle\" was not injected: check your FXML file 'comm_lcs.fxml'.";
        assert tcDate != null : "fx:id=\"tcDate\" was not injected: check your FXML file 'comm_lcs.fxml'.";
        assert tcAdmin != null : "fx:id=\"tcAdmin\" was not injected: check your FXML file 'comm_lcs.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'comm_lcs.fxml'.";
        assert tf_search != null : "fx:id=\"tf_search\" was not injected: check your FXML file 'comm_lcs.fxml'.";
        assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'comm_lcs.fxml'.";
        tableLcs.setFocusTraversable(false);
        
        Registry reg = LocateRegistry.getRegistry("localhost", 3333);
        service = (IComm_manageService)reg.lookup("com_manage");
        service1 = (ICommunityService)reg.lookup("community");
        
        list = service.getLicenseBoard();
        numList = service.getLicenseBoard();
        
        tcNo.setCellValueFactory(new PropertyValueFactory<>("lcs_id"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("lcs_nm"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("lcs_tdt"));
        tcAdmin.setCellValueFactory(new PropertyValueFactory<>("lcs_admId"));
        
        lcsSize = numList.size();
		paging();
        
		btnAdd.setFocusTraversable(false);
//		btnExit.setFocusTraversable(false);
    }
    
    public void getIndex() {
    	for (int i = 0; i < numList.size(); i++) {
    		LcsVO vo = new LcsVO();
    		vo.setLcs_admId(numList.get(i).getLcs_admId());
    		vo.setLcs_con(numList.get(i).getLcs_con());
    		vo.setLcs_id(numList.get(i).getLcs_id());
    		vo.setLcs_nm(numList.get(i).getLcs_nm());
    		vo.setLcs_tdt(numList.get(i).getLcs_tdt());
    		numList.set(i, vo);
		}
    }
    private void paging(){
    	int pageCount = lcsSize / rowPerPage + 
				(lcsSize%rowPerPage==0 ? 0 : 1);
		page.setPageCount(pageCount);
		page.setCurrentPageIndex(0);
		
		changeTableView(0);
		
		page.currentPageIndexProperty().addListener(
				new ChangeListener<Number>() {
					
					@Override
					public void changed(ObservableValue<? extends Number> observable, 
							Number oldValue, Number newValue) {
						changeTableView(newValue.intValue());
					}
				}
			);
    }
    public void changeTableView(int index) {
		int fromIndex = index * rowPerPage; // 가져올 데이터의 시작번호
		int toIndex = Math.min(fromIndex + rowPerPage, lcsSize);

		tableLcs.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));
	}
    private void getTableList() {
		Data = FXCollections.observableArrayList(list);
		tableLcs.setItems(Data);
	}
}
