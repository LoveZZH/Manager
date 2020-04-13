import cn.itcast.pojo.Admin;
import cn.itcast.web.RegisterController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class RegisterTest {
    @Autowired
    public RegisterController registerController;
    
    @Test
    public void register(){
        Admin admin = new Admin();
        admin.setUserId("1111");
        admin.setPassword("123456");
        String s = registerController.insertUser(admin);

    }
    
}
