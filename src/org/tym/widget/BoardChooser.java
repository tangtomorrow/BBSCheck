package org.tym.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.tym.entity.Board;
import org.tym.entity.Section;
import org.tym.util.DocUtil;

public class BoardChooser extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 543902545247601504L;
	private JComboBox<Section> combobox_section;
	private JComboBox<Board> combobox_board;
    private DefaultComboBoxModel<Section> model_section = new DefaultComboBoxModel<Section>();
	private DefaultComboBoxModel<Board> model_board = new DefaultComboBoxModel<Board>();
	
	public BoardChooser() {
		//Section
		for (Section sec : DocUtil.getSections()) {
			model_section.addElement(sec);
		}
		combobox_section = new JComboBox<Section>(model_section);
		combobox_section.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				@SuppressWarnings("unchecked")
				JComboBox<Section> source = (JComboBox<Section>) evt.getSource();
				Section sec = (Section) source.getSelectedItem();
				List<Board> boards = DocUtil.getBoardsBySection(sec);
				model_board.removeAllElements();
				for (Board bd : boards) {
					model_board.addElement(bd);
				}
			}
		});
		
		//Initial Board
		for (Board bd : DocUtil.getBoardsBySection(new Section(0))) {
			model_board.addElement(bd);
		}
		combobox_board = new JComboBox<Board>(model_board);
		
		//this.setLayout(new GridLayout(1, 2));
		this.add(combobox_section);
		this.add(combobox_board);
		this.setBorder(BorderFactory.createTitledBorder("选择版面"));
	}

	public JComboBox<Board> getCombobox_board() {
		return combobox_board;
	}

	public JComboBox<Section> getCombobox_section() {
		return combobox_section;
	}
}
