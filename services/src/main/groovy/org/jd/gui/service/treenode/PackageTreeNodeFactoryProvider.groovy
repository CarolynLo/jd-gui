/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.service.treenode

import org.jd.gui.api.API
import org.jd.gui.api.feature.ContainerEntryGettable
import org.jd.gui.api.feature.UriGettable
import org.jd.gui.api.model.Container
import org.jd.gui.util.JarContainerEntryUtil
import org.jd.gui.view.data.TreeNodeBean

import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode

class PackageTreeNodeFactoryProvider extends DirectoryTreeNodeFactoryProvider {
    static final ImageIcon ICON = new ImageIcon(PackageTreeNodeFactoryProvider.class.classLoader.getResource('org/jd/gui/images/package_obj.png'))

    /**
     * @return local + optional external selectors
     */
    String[] getSelectors() { ['jar:dir:*'] + externalSelectors }

    public <T extends DefaultMutableTreeNode & ContainerEntryGettable & UriGettable> T make(API api, Container.Entry entry) {
        int lastSlashIndex = entry.path.lastIndexOf('/')
        Collection<Container.Entry> entries = entry.children

        // Aggregate directory names
        while (entries.size() == 1) {
            Container.Entry child = entries[0]
            if (!child.isDirectory() || api.getTreeNodeFactory(child) != this || entry.container != child.container) break
            entry = child
            entries = entry.children
        }

        def label = entry.path.substring(lastSlashIndex+1).replace('/', '.')
        def node = new TreeNode(entry, new TreeNodeBean(label:label, icon:getIcon(), openIcon:getOpenIcon()))

        if (entries.size() > 0) {
            // Add dummy node
            node.add(new DefaultMutableTreeNode())
        }

        return node
    }

    ImageIcon getIcon() { ICON }
    ImageIcon getOpenIcon() { null }

    static class TreeNode extends DirectoryTreeNodeFactoryProvider.TreeNode {
        TreeNode(Container.Entry entry, Object userObject) {
            super(entry, userObject)
        }

        Collection<Container.Entry> getChildren() {
            return JarContainerEntryUtil.removeInnerTypeEntries(entry.children)
        }
    }
}
