package com.jamer.jaimeloyola.becas.RssParse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Jaime Loyola on 01/04/2016.
 *
 * Clase que representa al elemento <rss> del feed
 */

@Root(name = "rss", strict = false)
@Namespace(reference="http://search.yahoo.com/mrss/")
public class Rss {


    @Element
    private Channel channel;

    public Rss() {
    }

    public Rss(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}

