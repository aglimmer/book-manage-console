package won.page;

import won.model.User;
import won.query.UserQuery;

import java.util.Scanner;

import static java.lang.System.out;

/**
 * 对于User的操作窗口，主要调用query.BorrowRecordQuery里面的查询方法
 */
public class UserPage {

    public UserPage() {
    }

    public static User loginConsole() {
        User user = null;
        System.out.println("****************欢迎使用图书管理系统***************");
        while (true) {
            System.out.println("********************账户登录********************");
            out.println("提示：【1】已有账号，请登录 【2】没有账号，请注册");
            out.print("请选择方式：");
            user = null;
            Scanner input = new Scanner(System.in);
            String select = input.nextLine();
            out.print("账号：");
            String userid = input.nextLine();
            out.print("密码：");
            String password = input.nextLine();
            switch (select) {
                case "1":
                    user = UserQuery.queryUser(userid, password);
                    break;
                case "2":
                    user = UserQuery.registerUser(userid, password);
                    break;
            }
            if (user != null) {
                out.println("登录成功！");
                break;
            } else {
                out.println("账号或密码有误！");
                out.println("操作选项：【1】重新尝试，请输入1 \t【2】结束运行，请输入Enter键");
                out.print("请选择：");
                String msg = input.nextLine();
                if (!msg.equals("1")) {
                    System.exit(0);
                }
            }
        }
        return user;
    }

    public static User resetPassword() {
        System.out.println("********************重置密码********************");
        Scanner input = new Scanner(System.in);
        System.out.print("请输入原有的账号:");
        String username = input.nextLine();
        out.print("请输入原有的密码：");
        String password = input.nextLine();
        User user = UserQuery.queryUser(username, password);
        if (user != null) {
            out.print("请输入新密码：");
            String newPassword = input.nextLine();
            UserQuery.resetPassword(user.getId(), newPassword);
        } else {
            out.println("身份验证失败!");
        }
        return user;
    }
}
