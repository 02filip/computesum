package src.computeSum;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/*
 *  JUnit test
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    /*
     * Tests below check various input strings and the resulting status
     * input = 1,2,3 -> 200 OK
     * input = 100,12,5 -> 400 Bad Request
     * input = 1,-2,3 -> 400 Bad Request
     * input = 2, 13,5 -> 400 Bad Request
     * input = 1+5+6, delimiter = x -> 400 Bad Request
     */
    @Test
    public void testReturnSum() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1,2,3")).andDo(print()).andExpect(status().isOk());
    }
    
    @Test    
    public void testReturnError1() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=100,12,5")).andDo(print()).andExpect(status().isBadRequest());
    }
    
    @Test    
    public void testReturnError2() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1,-2,3")).andDo(print()).andExpect(status().isBadRequest());
    }
    
    @Test    
    public void testReturnError3() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1, 22,3")).andDo(print()).andExpect(status().isBadRequest());
    }
    
    @Test    
    public void testReturnError4() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1+5+6&delimiter=x")).andDo(print()).andExpect(status().isBadRequest());
    }
    
}