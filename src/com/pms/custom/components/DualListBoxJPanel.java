package com.pms.custom.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import com.pms.entity.ChannelDetails;
import com.pms.forms.NewEntryForm;
import com.pms.forms.UpdateEntryForm;
import com.pms.list.model.SortedListModel;

public class DualListBoxJPanel extends JPanel {

	/**
	* 
	*/
	private static final long serialVersionUID = 9063448592182885974L;

	private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

	private static final String ADD_BUTTON_LABEL = "ADD CHANNEL >>";

	private static final String REMOVE_BUTTON_LABEL = "<< REMOVE CHANNEL";

	private static final String CONFIRM_BUTTON_LABEL = "CONFIRM FEES FOR SELECTED CHANNELS ";

	private static final String BACK_BUTTON_LABEL = "BACK";

	private static final String DEFAULT_SOURCE_CHOICE_LABEL = "AVAILABLE CHANNELS:";

	private static final String DEFAULT_DEST_CHOICE_LABEL = "SELECTED CHANNELS:";

	private static final String TOTAL_FEES_LABEL = "TOTAL FEES FOR SELECTED CHANNELS:";

	private JLabel sourceLabel;

	private JList<ChannelDetails> sourceList;

	private SortedListModel sourceListModel;

	private JList<ChannelDetails> destList;

	private SortedListModel destListModel;

	private JLabel destLabel;

	private JButton addButton;

	private JButton removeButton;

	private JLabel totalFeesLabel;

	private JTextField feeText;

	private JButton confirmButton;

	private JButton backButton;

	private int sumOfChannelsSelected = 0;

	private Object parentClassInstance;

	private JFrame configureFeeFrame;

	public JFrame getParentFrame() {
		JFrame parentFrame = null;
		if (parentClassInstance instanceof NewEntryForm) {
			parentFrame = ((NewEntryForm) parentClassInstance).getFrame();
		} else if (parentClassInstance instanceof UpdateEntryForm) {
			parentFrame = ((UpdateEntryForm) parentClassInstance).getFrame();
		}
		return parentFrame;
	}

	public DualListBoxJPanel(List<ChannelDetails> sourceElements, List<ChannelDetails> destinationElements,
			Object parentClassInstance) {
		this.parentClassInstance = parentClassInstance;
		initScreen();
		addSourceElements(sourceElements);
		if (destinationElements != null) {
			addDestinationElements(destinationElements);
			addFees(destinationElements);
			feeText.setText(String.valueOf(sumOfChannelsSelected));
		}

	}

	public String getSourceChoicesTitle() {
		return sourceLabel.getText();
	}

	public void setSourceChoicesTitle(String newValue) {
		sourceLabel.setText(newValue);
	}

	public String getDestinationChoicesTitle() {
		return destLabel.getText();
	}

	public void setDestinationChoicesTitle(String newValue) {
		destLabel.setText(newValue);
	}

	public void clearSourceListModel() {
		sourceListModel.clear();
	}

	public void clearDestinationListModel() {
		destListModel.clear();
	}

	public void addSourceElements(ListModel<ChannelDetails> newValue) {
		fillListModel(sourceListModel, newValue);
	}

	public void setSourceElements(ListModel<ChannelDetails> newValue) {
		clearSourceListModel();
		addSourceElements(newValue);
	}

	public void addDestinationElements(ListModel<ChannelDetails> newValue) {
		fillListModel(destListModel, newValue);
	}

	private void fillListModel(SortedListModel model, ListModel<ChannelDetails> newValues) {
		int size = newValues.getSize();
		for (int i = 0; i < size; i++) {
			model.add(newValues.getElementAt(i));
		}
	}

	public void addSourceElements(List<ChannelDetails> newValues) {
		fillListModel(sourceListModel, newValues);
	}

	public void setSourceElements(List<ChannelDetails> newValues) {
		clearSourceListModel();
		addSourceElements(newValues);
	}

	public void addDestinationElements(List<ChannelDetails> newValues) {
		fillListModel(destListModel, newValues);
	}

	private void fillListModel(SortedListModel model, List<ChannelDetails> newValues) {
		model.addAll(newValues);
	}

	public Iterator<ChannelDetails> sourceIterator() {
		return sourceListModel.iterator();
	}

	public Iterator<ChannelDetails> destinationIterator() {
		return destListModel.iterator();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setSourceCellRenderer(ListCellRenderer newValue) {
		sourceList.setCellRenderer(newValue);
	}

	@SuppressWarnings("rawtypes")
	public ListCellRenderer getSourceCellRenderer() {
		return sourceList.getCellRenderer();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setDestinationCellRenderer(ListCellRenderer newValue) {
		destList.setCellRenderer(newValue);
	}

	@SuppressWarnings("rawtypes")
	public ListCellRenderer getDestinationCellRenderer() {
		return destList.getCellRenderer();
	}

	public void setVisibleRowCount(int newValue) {
		sourceList.setVisibleRowCount(newValue);
		destList.setVisibleRowCount(newValue);
	}

	public int getVisibleRowCount() {
		return sourceList.getVisibleRowCount();
	}

	public void setSelectionBackground(Color newValue) {
		sourceList.setSelectionBackground(newValue);
		destList.setSelectionBackground(newValue);
	}

	public Color getSelectionBackground() {
		return sourceList.getSelectionBackground();
	}

	public void setSelectionForeground(Color newValue) {
		sourceList.setSelectionForeground(newValue);
		destList.setSelectionForeground(newValue);
	}

	public Color getSelectionForeground() {
		return sourceList.getSelectionForeground();
	}

	private void clearSourceSelected() {
		List<ChannelDetails> selectedList = sourceList.getSelectedValuesList();
		for (int i = selectedList.size() - 1; i >= 0; --i) {
			sourceListModel.removeElement(selectedList.get(i));
		}
		sourceList.getSelectionModel().clearSelection();
	}

	private void clearDestinationSelected() {
		List<ChannelDetails> selectedList = destList.getSelectedValuesList();
		for (int i = selectedList.size() - 1; i >= 0; --i) {
			destListModel.removeElement(selectedList.get(i));
		}
		destList.getSelectionModel().clearSelection();
	}

	private void initScreen() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		configureFeeFrame = new JFrame("CONFIGURE-FEE-FRAME");
		configureFeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		configureFeeFrame.setResizable(false);
		configureFeeFrame.setVisible(true);
		configureFeeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		configureFeeFrame.setSize(screenSize);
		configureFeeFrame.add(this, BorderLayout.CENTER);
		getParentFrame().setVisible(false);

		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new GridBagLayout());
		setBackground(Color.CYAN);

		sourceLabel = new JLabel(DEFAULT_SOURCE_CHOICE_LABEL);
		sourceListModel = new SortedListModel();
		sourceList = new JList<ChannelDetails>(sourceListModel);
		add(sourceLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		add(new JScrollPane(sourceList), new GridBagConstraints(0, 1, 1, 2, .25, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));

		addButton = new JButton(ADD_BUTTON_LABEL);
		add(addButton, new GridBagConstraints(1, 1, 1, 2, 0, .5, GridBagConstraints.NORTH, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		addButton.addActionListener(new AddListener());

		removeButton = new JButton(REMOVE_BUTTON_LABEL);
		add(removeButton, new GridBagConstraints(1, 2, 1, 2, 0, .5, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		removeButton.addActionListener(new RemoveListener());

		destLabel = new JLabel(DEFAULT_DEST_CHOICE_LABEL);
		destListModel = new SortedListModel();
		destList = new JList<ChannelDetails>(destListModel);
		add(destLabel, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		add(new JScrollPane(destList), new GridBagConstraints(2, 1, 1, 3, .5, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));
		totalFeesLabel = new JLabel(TOTAL_FEES_LABEL);
		add(totalFeesLabel, new GridBagConstraints(2, 3, 1, 2, 0.25, 1, GridBagConstraints.WEST,
				GridBagConstraints.NONE, EMPTY_INSETS, 0, 0));
		feeText = new PMSJTextField(15);
		feeText.setEditable(false);
		feeText.setText(String.valueOf(sumOfChannelsSelected));
		add(feeText, new GridBagConstraints(2, 3, 1, 2, 0.25, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));

		confirmButton = new JButton(CONFIRM_BUTTON_LABEL);
		add(confirmButton, new GridBagConstraints(2, 3, 1, 2, 0.25, 1, GridBagConstraints.EAST, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		confirmButton.addActionListener(new ConfirmButtonListener());

		backButton = new JButton(BACK_BUTTON_LABEL);
		add(backButton, new GridBagConstraints(2, 5, 1, 2, 0.25, 1, GridBagConstraints.WEST, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		backButton.addActionListener(new BackButtonListener());

	}

	public class ConfirmButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			configureFeeFrame.setVisible(false);
			getParentFrame().setVisible(true);

			if (parentClassInstance instanceof NewEntryForm) {
				NewEntryForm newEntryForm = ((NewEntryForm) parentClassInstance);
				newEntryForm.getFeeText().setText(String.valueOf(sumOfChannelsSelected));
				newEntryForm.setSelectedChannels(destListModel.getAsList());
			} else if (parentClassInstance instanceof UpdateEntryForm) {
				UpdateEntryForm updateEntryForm = ((UpdateEntryForm) parentClassInstance);
				updateEntryForm.getFeeText().setText(String.valueOf(sumOfChannelsSelected));
				updateEntryForm.setSelectedChannels(destListModel.getAsList());
			}

		}

	}

	private class BackButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			configureFeeFrame.setVisible(false);
			getParentFrame().setVisible(true);
		}
	}

	private class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<ChannelDetails> selected = sourceList.getSelectedValuesList();
			addDestinationElements(selected);
			clearSourceSelected();
			addFees(selected);
			feeText.setText(String.valueOf(sumOfChannelsSelected));

		}
	}

	private class RemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<ChannelDetails> selected = destList.getSelectedValuesList();
			addSourceElements(selected);
			clearDestinationSelected();
			subtractFees(selected);
			feeText.setText(String.valueOf(sumOfChannelsSelected));

		}
	}

	public int addFees(List<ChannelDetails> channelList) {
		for (ChannelDetails channelDetails : channelList) {
			sumOfChannelsSelected = sumOfChannelsSelected + channelDetails.getChannelPrice();
		}
		return sumOfChannelsSelected;
	}

	public int subtractFees(List<ChannelDetails> channelList) {
		for (ChannelDetails channelDetails : channelList) {
			sumOfChannelsSelected = sumOfChannelsSelected - channelDetails.getChannelPrice();
		}
		return sumOfChannelsSelected;
	}

	/*
	 * public static void main(String args[]) {
	 * 
	 * List<ChannelDetails> channelDetailsList = new ArrayList<ChannelDetails>();
	 * ChannelDetails channelDetails1 = new ChannelDetails();
	 * channelDetails1.setChannelId(1);
	 * channelDetails1.setChannelName("Test Channel 1");
	 * channelDetails1.setChannelPrice(10); channelDetailsList.add(channelDetails1);
	 * ChannelDetails channelDetails2 = new ChannelDetails();
	 * channelDetails2.setChannelId(2);
	 * channelDetails2.setChannelName("Test Channel 2");
	 * channelDetails2.setChannelPrice(10); channelDetailsList.add(channelDetails2);
	 * 
	 * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	 * 
	 * 
	 * JFrame frame = new JFrame("Dual List Box Tester");
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.add(new
	 * JScrollBar()); DualListBoxJPanel dual = new
	 * DualListBoxJPanel(frame,channelDetailsList);
	 * 
	 * frame.add(dual, BorderLayout.CENTER); frame.setVisible(true);
	 * frame.setExtendedState(JFrame.MAXIMIZED_BOTH); frame.setSize(screenSize); }
	 */

}
