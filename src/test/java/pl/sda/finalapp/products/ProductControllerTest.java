package pl.sda.finalapp.products;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN") //mowimy, ze to powoduje 'zalogowanie' uzytkownika z rola admin
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)//czysci kontekst aplikacji, uruchamia ją od nowa
@ActiveProfiles("test")
class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void shouldDisplayProductPage() throws Exception {
        //given

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/products"))//sprawdzany url z kontorlera
                .andDo(MockMvcResultHandlers.print()) //wypisanie na kolsoli
                .andExpect(MockMvcResultMatchers.status().is(200)) // oczekiwany kod odpowiedzi
                .andReturn();//zwraca ModelAndView
        //then
        String viewName = mvcResult.getModelAndView().getViewName();
        Assertions.assertEquals(viewName, "productsPage");
        Assertions.assertEquals(((List) mvcResult.getModelAndView().getModel().get("productsList")).size(), 10);
    }

    @Test
    void shouldAddProductToDB() throws Exception {
        //given
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); //doczytać, ale chyba robi to co autowired
        long countBefore = productRepository.count();
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/products")
                .param("title", "t") //tutaj mockujemy dane ktore przychodza z web
                .param("pictureUrl", "pUrl")
                .param("price", "4.5")
                .param("productType", "BOOK")
                .param("categoryId", "3");

        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/products");
        //when
        MvcResult postResult = mockMvc.perform(postRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"))
                .andReturn();

        MvcResult getResult = mockMvc.perform(getRequest)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //then
        Assertions.assertEquals(countBefore + 1, productRepository.count());
    }

}
