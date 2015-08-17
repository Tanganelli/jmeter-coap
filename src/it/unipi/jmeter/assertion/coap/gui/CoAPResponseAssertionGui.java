package it.unipi.jmeter.assertion.coap.gui;

import it.unipi.jmeter.assertion.coap.CoAPResponseAssertion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.AbstractAssertionGui;
import org.apache.jmeter.gui.AbstractScopedJMeterGuiComponent;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.gui.util.TextAreaCellRenderer;
import org.apache.jmeter.gui.util.TextAreaTableCellEditor;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.util.JMeterUtils;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;


public class CoAPResponseAssertionGui extends AbstractAssertionGui {
	/** The name of the table column in the list of patterns. */
	private static final String COL_NAME = "CoAP Response Payload"; //$NON-NLS-1$

	/**
	 * Radio button indicating to test if the field contains one of the
	 * patterns.
	 */
	private JRadioButton containsBox;

	/**
	 * Radio button indicating to test if the field matches one of the patterns.
	 */
	private JRadioButton matchesBox;

	/**
	 * Radio button indicating if the field equals the first pattern.
	 */
	private JRadioButton equalsBox;

    /**
	 * Checkbox indicating to test that the field does NOT contain/match the
	 * patterns.
	 */
	private JCheckBox notBox;
	
	/** A table of patterns to test against. */
	private JTable stringTable;

	/** Button to add a new pattern. */
	private JButton addPattern;

	/** Button to delete a pattern. */
	private JButton deletePattern;

	/** Table model for the pattern table. */
	private PowerTableModel tableModel;

	private CoAPResponseAssertionMessagePanel messagePanel;

	private CoAPResponseAssertionOptionPanel optionPanel;

	/**
	 * Create a new AssertionGui panel.
	 */
	public CoAPResponseAssertionGui() {
		messagePanel = new CoAPResponseAssertionMessagePanel();
		optionPanel = new CoAPResponseAssertionOptionPanel();
		List<Type> types = new ArrayList<Type>();
		types.add(Type.CON);
		types.add(Type.NON);
		types.add(Type.ACK);
		types.add(Type.RST);
		messagePanel.initCmbType(types);
		List<ResponseCode> codes = new ArrayList<ResponseCode>();
		for (ResponseCode code:ResponseCode.values())
			codes.add(code);
		messagePanel.initCmbCode(codes);
		Set<Integer> registry = MediaTypeRegistry.getAllMediaTypes();
		List<String> media_types = new ArrayList<String>();
		media_types.add("");
		for(Integer i : registry)
			media_types.add(MediaTypeRegistry.toString(i));
		optionPanel.initCmbAccept(media_types);
		optionPanel.initCmbContentFormat(media_types);
		init();
	}

	@Override
    public String getStaticLabel() {
        return "CoAP Response Assertion";
    }

    @Override
    public String getLabelResource() {
        return getClass().getCanonicalName();
    }


	/* Implements JMeterGUIComponent.createTestElement() */
	@Override
	public TestElement createTestElement() {
		CoAPResponseAssertion el = new CoAPResponseAssertion();
		modifyTestElement(el);
		return el;
	}

	/* Implements JMeterGUIComponent.modifyTestElement(TestElement) */
	@Override
	public void modifyTestElement(TestElement el) {
		configureTestElement(el);
		if (el instanceof CoAPResponseAssertion) {
			CoAPResponseAssertion ra = (CoAPResponseAssertion) el;

			ra.clearTestStrings();
			String[] testStrings = tableModel.getData().getColumn(COL_NAME);
			for (int i = 0; i < testStrings.length; i++) {
				ra.addTestString(testStrings[i]);
			}

			if (containsBox.isSelected()) {
				ra.setToContainsType();
			} else if (equalsBox.isSelected()) {
                ra.setToEqualsType();
			} else {
				ra.setToMatchType();
			}

			if (notBox.isSelected()) {
				ra.setToNotType();
			} else {
				ra.unsetNotType();
			}
			
			ra.setMid(messagePanel.getTxtMid());
			ra.setToken(messagePanel.getTxtToken());
			try{
				ra.setResponseCode(messagePanel.getCmbCode().toString());
			}catch(NullPointerException e){
				ra.setResponseCode("");
			}
			try{
				ra.setType(messagePanel.getCmbType().toString());
			}catch(NullPointerException e){
				ra.setType("");
			}
			
			ra.setETag(optionPanel.getTxtETag());
			ra.setIfMatch(optionPanel.getTxtIfMatch());
			ra.setUriHost(optionPanel.getTxtUriHost());
			ra.setObserve(optionPanel.getTxtObserve());
			ra.setUriPort(optionPanel.getTxtUriPort());
			ra.setProxyUri(optionPanel.getTxtProxyUri());
			try{
				ra.setAccept( (String) optionPanel.getCmbAccept());
			}catch(NullPointerException e){
				ra.setAccept("");
			}
			try{
				ra.setContentFormat( (String) optionPanel.getCmbContentFormat());
			}catch(NullPointerException e){
				ra.setContentFormat("");
			}
			ra.setIfNoneMatch(optionPanel.getChkIfNoneMatch());
			ra.setProxySchema(optionPanel.getChkProxySchema());

		}
	}
    
    /**
     * Implements JMeterGUIComponent.clearGui
     */
	@Override
    public void clearGui() {
        super.clearGui();	
        
        messagePanel.setTxtMid("");
		messagePanel.setTxtToken("");
		try{
			messagePanel.setCmbCode("");
		}catch(IllegalArgumentException e){}
		try{
			messagePanel.setCmbType("");
		}catch(IllegalArgumentException e){}
		
		try{
			optionPanel.setCmbAccept("");
		}catch(IllegalArgumentException e){}
		try{
			optionPanel.setCmbContentFormat("");
		}catch(IllegalArgumentException e){}
		optionPanel.setTxtETag("");
		optionPanel.setTxtIfMatch("");
		optionPanel.setTxtObserve("");
		optionPanel.setTxtProxyUri("");
		optionPanel.setTxtUriHost("");
		optionPanel.setTxtUriPort("");
		optionPanel.setChkIfNoneMatch(false);
		optionPanel.setChkProxySchema(false);
		
        containsBox.setSelected(true);
        matchesBox.setSelected(false);
        equalsBox.setSelected(false);
        notBox.setSelected(false);
    }    

	/**
	 * A newly created component can be initialized with the contents of a Test
	 * Element object by calling this method. The component is responsible for
	 * querying the Test Element object for the relevant information to display
	 * in its GUI.
	 * 
	 * @param el
	 *            the TestElement to configure
	 */
    @Override
	public void configure(TestElement el) {
		super.configure(el);
		CoAPResponseAssertion model = (CoAPResponseAssertion) el;

		if (model.isContainsType()) {
			containsBox.setSelected(true);
			matchesBox.setSelected(false);
            equalsBox.setSelected(false);
        } else if (model.isEqualsType()) {
			containsBox.setSelected(false);
			matchesBox.setSelected(false);
            equalsBox.setSelected(true);
		} else {
			containsBox.setSelected(false);
			matchesBox.setSelected(true);
            equalsBox.setSelected(false);
		}

		if (model.isNotType()) {
			notBox.setSelected(true);
		} else {
			notBox.setSelected(false);
		}

		tableModel.clearData();
		PropertyIterator tests = model.getTestStrings().iterator();
		while (tests.hasNext()) {
			tableModel.addRow(new Object[] { tests.next().getStringValue() });
		}

		if (model.getTestStrings().size() == 0) {
			deletePattern.setEnabled(false);
		} else {
			deletePattern.setEnabled(true);
		}

		tableModel.fireTableDataChanged();
		
		messagePanel.setTxtMid(model.getMid());
		messagePanel.setTxtToken(model.getToken());
		try{
			messagePanel.setCmbCode(model.getResponseCode());
		}catch(IllegalArgumentException e){
			messagePanel.setCmbCode("");
		}catch(NullPointerException e){
			messagePanel.setCmbCode("");
		}
		try{
			messagePanel.setCmbType(Type.valueOf(model.getType().toString()));
		}catch(IllegalArgumentException e){
			messagePanel.setCmbType("");
		}catch(NullPointerException e){
			messagePanel.setCmbType("");
		}
		
		try{
			optionPanel.setCmbAccept(model.getAccept());
		}catch(IllegalArgumentException e){
			optionPanel.setCmbAccept("");
		}catch(NullPointerException e){
			optionPanel.setCmbAccept("");
		}
		try{
			optionPanel.setCmbContentFormat(model.getContentFormat());
		}catch(IllegalArgumentException e){
			optionPanel.setCmbContentFormat("");
		}catch(NullPointerException e){
			optionPanel.setCmbContentFormat("");
		}
		optionPanel.setTxtETag(model.getETag());
		optionPanel.setTxtIfMatch(model.getIfMatch());
		optionPanel.setTxtObserve(model.getObserve());
		optionPanel.setTxtProxyUri(model.getProxyUri());
		optionPanel.setTxtUriHost(model.getUriHost());
		optionPanel.setTxtUriPort(model.getUriPort());
		optionPanel.setChkIfNoneMatch(model.isIfNoneMatch());
		optionPanel.setChkProxySchema(model.isProxySchema());
	}

	/**
	 * Initialize the GUI components and layout.
	 */
	private void init() {
		setLayout(new BorderLayout());
		Box box = Box.createVerticalBox();
		setBorder(makeBorder());

		box.add(makeTitlePanel());
		box.add(messagePanel);
		box.add(optionPanel);
		box.add(createTypePanel());
		add(box, BorderLayout.NORTH);
		add(createStringPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * Create a panel allowing the user to choose what type of test should be
	 * performed.
	 * 
	 * @return a new panel for selecting the type of assertion test
	 */
	private JPanel createTypePanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Payload Matching Rules")); //$NON-NLS-1$

		ButtonGroup group = new ButtonGroup();

		containsBox = new JRadioButton(JMeterUtils.getResString("assertion_contains")); //$NON-NLS-1$
		group.add(containsBox);
		containsBox.setSelected(true);
		panel.add(containsBox);

		matchesBox = new JRadioButton(JMeterUtils.getResString("assertion_matches")); //$NON-NLS-1$
		group.add(matchesBox);
		panel.add(matchesBox);

		equalsBox = new JRadioButton(JMeterUtils.getResString("assertion_equals")); //$NON-NLS-1$
		group.add(equalsBox);
		panel.add(equalsBox);

		notBox = new JCheckBox(JMeterUtils.getResString("assertion_not")); //$NON-NLS-1$
		panel.add(notBox);

		return panel;
	}


	/**
	 * Create a panel allowing the user to supply a list of string patterns to
	 * test against.
	 * 
	 * @return a new panel for adding string patterns
	 */
	private JPanel createStringPanel() {
		tableModel = new PowerTableModel(new String[] { COL_NAME }, new Class[] { String.class });
		stringTable = new JTable(tableModel);

		TextAreaCellRenderer renderer = new TextAreaCellRenderer();
		stringTable.setRowHeight(renderer.getPreferredHeight());
		stringTable.setDefaultRenderer(String.class, renderer);
		stringTable.setDefaultEditor(String.class, new TextAreaTableCellEditor());
		stringTable.setPreferredScrollableViewportSize(new Dimension(100, 70));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Payloads to test")); //$NON-NLS-1$

		panel.add(new JScrollPane(stringTable), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Create a panel with buttons to add and delete string patterns.
	 * 
	 * @return the new panel with add and delete buttons
	 */
	private JPanel createButtonPanel() {
		addPattern = new JButton(JMeterUtils.getResString("add")); //$NON-NLS-1$
		addPattern.addActionListener(new AddPatternListener());

		deletePattern = new JButton(JMeterUtils.getResString("delete")); //$NON-NLS-1$
		deletePattern.addActionListener(new ClearPatternsListener());
		deletePattern.setEnabled(false);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addPattern);
		buttonPanel.add(deletePattern);
		return buttonPanel;
	}

	/**
	 * An ActionListener for deleting a pattern.
	 * 
	 */
	private class ClearPatternsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = stringTable.getSelectedRow();
			if (index > -1) {
				stringTable.getCellEditor(index, stringTable.getSelectedColumn()).cancelCellEditing();
				tableModel.removeRow(index);
				tableModel.fireTableDataChanged();
			}
			if (stringTable.getModel().getRowCount() == 0) {
				deletePattern.setEnabled(false);
			}
		}
	}

	/**
	 * An ActionListener for adding a pattern.
	 * 
	 */
	private class AddPatternListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tableModel.addNewRow();
			deletePattern.setEnabled(true);
			tableModel.fireTableDataChanged();
		}
	}
}