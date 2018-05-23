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
import static org.junit.matchers.JUnitMatchers.*;
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
     * Tests below check various input strings and the resulting status and value
     * In case of error, the message (reason) is checked if it is correct
     */
    @Test
    public void testReturnSum1() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1,2,3"))
        .andDo(print())
        .andExpect(status().isOk());
    }
    
    @Test
    public void testReturnSum2() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1"))
        .andDo(print())
        .andExpect(status().isOk());
    }
    
    @Test
    public void testReturnSum3() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=10;10;10;10;10&delimiter=;"))
        .andDo(print())
        .andExpect(status().isOk());
    }
    
    @Test
    public void testReturnSum4() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1x2x3&delimiter=x"))
        .andDo(print())
        .andExpect(status().isOk());
    }
    
    @Test
    public void testReturnSum5() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1 2 3&delimiter= "))
        .andDo(print())
        .andExpect(status().isOk());
    }   
    
    @Test
    public void testReturnSum6() throws Exception {
        this.mockMvc.perform(get("/computeSum?input="))
        .andDo(print())
        .andExpect(status().isOk());
    }
    
    @SuppressWarnings("deprecation")
	@Test    
    public void testReturnError1() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1;2;3&delimiter=."))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Invalid delimiter")));
    }

	@SuppressWarnings("deprecation")
	@Test    
    public void testReturnError2() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1,2,3&delimiter=X"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Invalid delimiter")));
    }
    
    @SuppressWarnings("deprecation")
	@Test    
    public void testReturnError3() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=1 2 3"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Invalid delimiter")));
    }
    
    @SuppressWarnings("deprecation")
	@Test    
    public void testReturnError4() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=-12,10"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Negative number not supported")));
    }
    
    @SuppressWarnings("deprecation")
	@Test    
    public void testReturnError5() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=101,10"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Number higher than 100 not supported")));
    }
    
    @SuppressWarnings("deprecation")
	@Test    
    public void testReturnError6() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=Abc1%"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Invalid input")));
    }
    
    @SuppressWarnings("deprecation")
	@Test    
    public void testReturnError7() throws Exception {
        this.mockMvc.perform(get("/computeSum?input=12, 23,11"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Invalid input")));
    }
}