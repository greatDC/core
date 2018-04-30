package com.dotmarketing.portlets.htmlpageasset.business.render;

import com.dotcms.repackage.com.google.common.collect.ImmutableList;
import com.dotmarketing.beans.ContainerStructure;
import com.dotmarketing.beans.MultiTree;
import com.dotmarketing.portlets.containers.model.Container;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the information of the {@link Container} and its respective
 * {@link ContainerStructure} relationships. These relationships define what Content Types can be
 * added to the container.
 *
 * @author Will Ezell
 * @author Jose Castro
 * @version 4.2
 * @since Oct 6, 2017
 */
public class ContainerRendered implements Serializable {

    private static final long serialVersionUID = 1572918359580445566L;

    private final Container container;
    private final List<ContainerStructure> containerStructures;
    private String rendered;
    private final String uuid;
    /**
     * Creates a new instance of the ContainerRendered.
     *
     * @param container           The {@link Container} in the HTML Page.
     * @param containerStructures The list of {@link ContainerStructure} relationships.
     *                           the browser.
     */
    public ContainerRendered(final Container container, final List<ContainerStructure> containerStructures, final String uuid) {
        this.container = container;
        this.uuid=(uuid==null) ? MultiTree.LEGACY_RELATION_TYPE : uuid;
        if (containerStructures != null) {
            this.containerStructures = ImmutableList.copyOf(containerStructures);
        } else {
            this.containerStructures = ImmutableList.of();
        }
    }

    /**
     * Returns the page container.
     *
     * @return The {@link Container} in the page.
     */
    public Container getContainer() {
        return container;
    }

    /**
     * Returns the {@link Container} as rendered in the browser.
     *
     * @return The HTML representation of the container.
     */
    public String getRendered() {
        return rendered;
    }

    /**
     * Returns the relationships that determine what Content Types can be added to a specific
     * Container.
     *
     * @return The list of {@link ContainerStructure} relationships.
     */
    public List<ContainerStructure> getContainerStructures() {
        return containerStructures;
    }

    @Override
    public String toString() {
        return "ContainerRendered{" + "container=" + container + ", containerStructures=" +
                containerStructures + ", rendered='" + rendered + '\'' + '}';
    }

    /**
     * Set the container and its contents as HTML code, i.e., as seen in
     *
     * @param rendered
     */
    public void setRendered(final String rendered) {
        this.rendered = rendered;
    }

    public String getUuid() {
        return uuid;
    }
}
