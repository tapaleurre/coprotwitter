package therealtwitter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "Tweets")
public class Tweets {

    @XmlElement
    public ArrayList<Tweet> liste = new ArrayList<Tweet>();
}
