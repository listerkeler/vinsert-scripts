package org.listerkeler.scripts.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.vinsert.bot.script.Script;
import org.vinsert.bot.script.ScriptManifest;
import org.vinsert.bot.script.api.Widget;


@ScriptManifest(authors = { "Kaden" }, name = "Interface Explorer", description = "Fetches various interface data for developers.")
public class InterfaceExplorer extends Script {

    JFrame frame;
    JTree tree;
    InterfaceTreeModel treeModel;

    Widget parent = null;
    Widget component = null;

    public void initGUI(){
        frame = new JFrame("Interface Explorer - vInsert");
        frame.setPreferredSize(new Dimension(450, 350));

        treeModel = new InterfaceTreeModel();
        treeModel.update();

        tree = new JTree(treeModel);
        tree.setRootVisible(false);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(final TreeSelectionEvent e) {
                final Object node = tree.getLastSelectedPathComponent();

                if (node == null) {
                    return;
                }

                if(node instanceof WidgetWrapper){

                    parent = ((WidgetWrapper)node).getWrapped();
                }

                Widget iface = null;
                if (node instanceof ComponentWrapper) {
                    component = ((ComponentWrapper) node).getWrapped();
                    parent = widgets.getValidated().get(component.getParentId());
                    iface = ((ComponentWrapper) node).getWrapped();
                }
                if (iface == null) {
                    return;
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel toolPanel = new JPanel();

        final ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                treeModel.update();
                log("Update Action Listener - Tree Updated!");
            }
        };

        final JButton updateButton = new JButton("Update");
        updateButton.addActionListener(actionListener);
        toolPanel.add(updateButton, BorderLayout.CENTER);
        frame.add(toolPanel, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);

    }


    @Override
    public boolean init() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initGUI();
            }
        });
        return true;
    }

    @Override
    public void render(Graphics2D graphics) {

        if(parent != null){
            graphics.setColor(Color.ORANGE);
            Rectangle bounds = parent.getBounds();
            graphics.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        if(component != null){
            graphics.setColor(Color.BLUE);
            Rectangle bounds = component.getBounds();
            graphics.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

    }

    @Override
    public int pulse() { return 1000; }

    @Override
    public void close() { }

    private class InterfaceTreeModel implements TreeModel {
        private final Object root = new Object();
        private final ArrayList<TreeModelListener> treeModelListeners = new ArrayList<TreeModelListener>();

        // only call getAllInterfaces() once per GUI update, because
        // otherwise closed interfaces might mess up the indexes
        private final ArrayList<WidgetWrapper> widgetGroup = new ArrayList<WidgetWrapper>();

        public void addTreeModelListener(final TreeModelListener l) {
            treeModelListeners.add(l);
        }

        private void fireTreeStructureChanged(final Object oldRoot) {
            treeModelListeners.size();
            final TreeModelEvent e = new TreeModelEvent(this, new Object[] { oldRoot });
            for (final TreeModelListener tml : treeModelListeners) {
                tml.treeStructureChanged(e);
            }
        }

        public Object getChild(final Object parent, final int index) {
            if (parent == root) {
                return widgetGroup.get(index);
            } else if (parent instanceof WidgetWrapper) {
                return new ComponentWrapper(
                        ((WidgetWrapper) parent).wrapped.getChildren().get(index));
            } else if (parent instanceof ComponentWrapper) {
                return new ComponentWrapper(
                        ((ComponentWrapper) parent).wrapped.getChildren().get(index));
            }
            return null;
        }

        public int getChildCount(final Object parent) {
            if (parent == root) {
                return widgetGroup.size();
            } else if (parent instanceof WidgetWrapper) {
                return ((WidgetWrapper) parent).wrapped.getChildren().size();
            } else if (parent instanceof WidgetWrapper) {
                return ((WidgetWrapper) parent).wrapped.getChildren().size();
            }
            return 0;
        }

        public int getIndexOfChild(final Object parent, final Object child) {
            if (parent == root) {
                return widgetGroup.indexOf(child);
            } else if (parent instanceof WidgetWrapper) {
                return Arrays.asList( ((WidgetWrapper) parent).wrapped.getChildren()).indexOf( ((ComponentWrapper) child).wrapped);
            } else if (parent instanceof ComponentWrapper) {
                return Arrays.asList( ((ComponentWrapper) parent).wrapped.getChildren()) .indexOf(((ComponentWrapper) child).wrapped);
            }
            return -1;
        }

        public Object getRoot() {
            return root;
        }

        public boolean isLeaf(final Object o) {
            return o instanceof ComponentWrapper && ((ComponentWrapper) o).wrapped.getChildren().size() == 0;
        }

        public void removeTreeModelListener(final TreeModelListener l) {
            treeModelListeners.remove(l);
        }

        public void update() {
            widgetGroup.clear();

            List<Widget> w = widgets.getValidated();
            //log(w.length);
            for (Widget iface : w) {
                widgetGroup.add(new WidgetWrapper(iface));
            }
            fireTreeStructureChanged(root);
        }

        public void valueForPathChanged(final TreePath path, final Object newValue) {
            // tree represented by this model isn't editable
        }
    }

    /*
     *
     * The Wrapper for the WidgetGroup. So we have toString() for the names.
     *
     */
    public class WidgetWrapper {
        private Widget wrapped;

        public WidgetWrapper(Widget wrapped){
            this.wrapped = wrapped;
        }

        public Widget getWrapped(){
            return wrapped;
        }

        @Override
        public boolean equals(final Object o) {
            return o instanceof WidgetWrapper && wrapped == ((WidgetWrapper) o).wrapped;
        }

        @Override
        public String toString() {
            return "Interface " + wrapped.getParentId() + " - " + wrapped.getId();
        }
    }

    /*
     *
     * The wrapper for the Components within the WidgetGroup. (and their components).
     *
     */
    public class ComponentWrapper {
        private Widget wrapped;


        public ComponentWrapper(Widget wrapped){
            this.wrapped = wrapped;
        }

        public Widget getWrapped(){
            return wrapped;
        }

        @Override
        public boolean equals(final Object o) {
            return o instanceof ComponentWrapper && wrapped == ((ComponentWrapper) o).wrapped;
        }

        @Override
        public String toString() {
            return "Component " + wrapped.getId();
        }
    }


}