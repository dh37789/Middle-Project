package campusServerMain;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import admin.classroom_manage.comm.service.AdminClassCommServiceImpl;
import admin.classroom_manage.comm.service.IadminClassCommSerivce;
import admin.classroom_manage.question.service.AdminQuizServiceImpl;
import admin.classroom_manage.question.service.IAdminQuizService;
import admin.classroom_manage.video.service.AdminVideoServiceImpl;
import admin.classroom_manage.video.service.IadminVideoService;
import admin.comm_manage.service.Comm_manageServiceImpl;
import admin.comm_manage.service.IComm_manageService;
import admin.lectBoard_manage.service.ILectBoard_manageService;
import admin.lectBoard_manage.service.LectBoard_manageServiceImpl;
import admin.lectinfo_manage.service.ILectinfo_manageService;
import admin.lectinfo_manage.service.Lectinfo_manageServiceImpl;
import admin.mem_manage.service.IMem_manageService;
import admin.mem_manage.service.Mem_manageServiceImpl;
import classRoom.board.service.BoardServiceImpl;
import classRoom.board.service.IBoardService;
import classRoom.main.service.ClassMainServiceImpl;
import classRoom.main.service.IClassMainService;
import classRoom.question.service.IQuestionService;
import classRoom.question.service.QuestionServiceImpl;
import classRoom.video.service.IVideoService;
import classRoom.video.service.VideoServiceImpl;
import home.attend.service.AttendServiceImpl;
import home.attend.service.IAttendService;
import home.community.service.CommunityServiceImpl;
import home.community.service.FreeBoardServiceImpl;
import home.community.service.ICommunityService;
import home.community.service.IFreeBoardService;
import home.inquire.service.IInquireService;
import home.inquire.service.InquireServiceImpl;
import home.lecture_info.service.ILecture_infoService;
import home.lecture_info.service.Lecture_infoServiceImpl;
import home.main.service.HomeServiceImpl;
import home.main.service.IHomeService;
import home.mem_info.service.IMem_infoService;
import home.mem_info.service.Mem_infoServiceImpl;
import login.main.service.ILoginService;
import login.main.service.LoginServiceImpl;
import login.register.service.IRegisterService;
import login.register.service.RegisterServiceImpl;
import login.search.service.ISearchService;
import login.search.service.SearchServiceImpl;

public class CampusServerMain {
	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.createRegistry(3333);
			
			// admin
			IadminClassCommSerivce adminClassComm = new AdminClassCommServiceImpl();
			IAdminQuizService quizService = new AdminQuizServiceImpl();
			IComm_manageService coManaService = new Comm_manageServiceImpl();
			ILectBoard_manageService lectMaService = new LectBoard_manageServiceImpl();
			ILectinfo_manageService lectInfoMaService = new Lectinfo_manageServiceImpl();
			IMem_manageService memManaService = new Mem_manageServiceImpl();
			IadminVideoService videoservice = new AdminVideoServiceImpl();
			
			// classRoom
			IBoardService boardService = new BoardServiceImpl();
			IQuestionService queService = new QuestionServiceImpl();
			IVideoService vdService = new VideoServiceImpl();
			IClassMainService cMainService = new ClassMainServiceImpl();
			
			// home
			IAttendService attService = new AttendServiceImpl();
			ICommunityService comuService = new CommunityServiceImpl();
			IInquireService inqService = new InquireServiceImpl();
			ILecture_infoService lectInfoService = new Lecture_infoServiceImpl();
			IHomeService homeService = new HomeServiceImpl();
			IMem_infoService memService = new Mem_infoServiceImpl();
			IFreeBoardService fbservice = new FreeBoardServiceImpl();
			
			// login
			ILoginService loginService = new LoginServiceImpl();
			IRegisterService regiService = new RegisterServiceImpl();
			ISearchService searService = new SearchServiceImpl();
			
			// admin
			reg.rebind("adminClass", adminClassComm);
			reg.rebind("adminQuiz", quizService);
			reg.rebind("com_manage", coManaService);
			reg.rebind("lectBoardMana", lectMaService);
			reg.rebind("lectInfo", lectInfoMaService);
			reg.rebind("memMana", memManaService);
			reg.rebind("admin_video", videoservice);
			
			// classRoom
			reg.rebind("board", boardService);
			reg.rebind("question", queService);
			reg.rebind("vdService", vdService);
			reg.rebind("classMain", cMainService);
			
			// home
			reg.rebind("attend", attService);
			reg.rebind("community", comuService);
			reg.rebind("inquire", inqService);
			reg.rebind("lecture_info", lectInfoService);
			reg.rebind("mainHome", homeService);
			reg.rebind("memberInfo", memService);
			reg.rebind("freeboard", fbservice);
			
			// login
			reg.rebind("login", loginService);
			reg.rebind("register", regiService);
			reg.rebind("search", searService);
			System.out.println("서버 준비 완료");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
