package won.page;

import won.model.User;
import won.page.BookPage;
import won.page.BorrowRecordPage;
import won.page.UserPage;

import java.util.Scanner;

import static java.lang.System.out;
/**
 * 简单的控制台命令窗口界面，列出一系列功能
 **/
public class Home {
	Scanner input = new Scanner(System.in);

	public Home() {
		//测试账号
		//User user = new User(8,"xiali","111111");
		//进行登录，登录成功则返回一个用户实体
		User user =  UserPage.loginConsole();
		while (true) {
			out.println("*******************图书管理系统 ******************");
			out.println("*                 【1】今日新书                  *");
			out.println("*                 【2】查询图书                  *");
			out.println("*                 【3】借阅图书                  *");
			out.println("*                 【4】归还图书                  *");
			out.println("*                 【5】添加新书                  *");
			out.println("*                 【6】修改图书                  *");
			out.println("*                 【7】删除图书                  *");
			out.println("*                 【8】重置密码                  *");
			out.println("*                 【0】退出系统                  *");
			out.println("*************************************************");
			out.print("请选择功能：");
			String select = input.nextLine();
			switch (select) {
				case "1":
					BookPage.getLatestBook();
					break;
				case "2":
					BookPage.queryBook();
					break;
				case "3":
					BorrowRecordPage.borrowBookDetail(user);
					break;
				case "4":
					BorrowRecordPage.returnBook(user);
					break;
				case "5":
					BookPage.addBook();
					break;
				case "6":
					BookPage.modifyBook();
					break;
				case "7":
					BookPage.deleteBook();
					break;
				case "8":
					User newUser = UserPage.resetPassword();
					user = newUser==null?user:newUser;
					break;
				case "0":
					out.println("*******************已退出系统!!!*****************");
					System.exit(0);
					break;
			}
			out.print("按Enter键键继续...");
			input.nextLine();
		}
	}
}












