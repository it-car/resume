package xyz.minicar.util;

import org.junit.Assert;
import org.junit.Test;
import xyz.minic.xr.util.Strings;

/**
 * @author minic
 * @date 2022/07/22 09:27
 **/
public class StringTest {
    @Test
    public void underlineCase() {
        Assert.assertTrue(Strings.underlineCase("MyFirstAge").equals("my_first_age"));
        Assert.assertTrue(Strings.underlineCase("myFirstAge").equals("my_first_age"));
    }
}
