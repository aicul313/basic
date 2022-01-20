import java.util.*;

public class IDandPasswords {

    HashMap<String,String> logininfo = new HashMap<String,String>();

    IDandPasswords(){

        logininfo.put("Hyeonji", "imhungry");
        logininfo.put("wjdguswl", "sohungry");
        logininfo.put("guswl", "iwantpizza");

    }

    protected HashMap getLoginInfo(){

        return logininfo;

    }
    
}
