package com.jamer.jaimeloyola.becas.RssParse;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

/**
 * Created by Jaime Loyola on 01/04/2016.
 *
 * Clase que representa la etiqueta <channel></channel> del feed
 */

@Root(name = "channel", strict = false)
public class Channel {

    @ElementList(inline = true)
    private List<Item> items;

    public Channel() {
    }
    public Channel(List<Item> items) { this.items = items; }
    public List<Item> getItems() { return items; }
}
