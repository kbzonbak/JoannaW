package bbr.b2b.extractors.walmart.dto;

import java.util.Date;

import org.openqa.selenium.WebElement;

public class WebElementDTO {
    private Date date;
    private WebElement webElement;


    public WebElementDTO(Date date, WebElement webElement) {
        this.date = date;
        this.webElement = webElement;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
