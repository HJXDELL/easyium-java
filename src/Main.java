import com.iselsoft.easyium.LocatorHelper;
import com.iselsoft.easyium.exceptions.InvalidLocatorException;

public class Main {
    
    public static void main(String... args) throws InvalidLocatorException {
        
        for (int i=0;i< 100000;i++) {
            System.out.print(LocatorHelper.toBy("css=xxxx"));
        }
    }
}
