import com.dev1d3.sdk.CheckoutPage;
import com.dev1d3.sdk.SignatureHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class CheckoutPageTest
{
    private CheckoutPage checkoutPage;

    @Before
    public void initTest()
    {
        checkoutPage = new CheckoutPage(new SignatureHandler(TestFixtures.secret));
    }

    @After
    public void afterTest()
    {
        checkoutPage = null;
    }

    @Test
    public void setBaseUrl()
    {
        assertEquals(checkoutPage, checkoutPage.setBaseUrl(TestFixtures.testUrl));
        assertEquals(TestFixtures.testUrl.concat(TestFixtures.compareParams), checkoutPage.getUrl(TestFixtures.getPayment()));
    }

    @Test
    public void getUrl()
    {
        assertEquals(TestFixtures.baseUrl.concat(TestFixtures.compareParams), checkoutPage.getUrl(TestFixtures.getPayment()));
    }

    @Test(expected = InvocationTargetException.class)
    public void encode() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException
    {
        Field field = checkoutPage.getClass().getDeclaredField("CHARSET");
        field.setAccessible(true);
        field.set(checkoutPage, "");

        Method method = checkoutPage.getClass().getDeclaredMethod("encode", Object.class);
        method.setAccessible(true);
        method.invoke(checkoutPage, "test/test");
    }
}
