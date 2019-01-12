package com.pms.list.model;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

import com.pms.entity.ChannelDetails;


public class SortedListModel extends AbstractListModel<ChannelDetails> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1676979980138787332L;
	SortedSet<ChannelDetails> model;

	public SortedListModel() {
		model = new TreeSet<ChannelDetails>();
	}

	public int getSize() {
		return model.size();
	}


	public ChannelDetails getElementAt(int index) {
		return (ChannelDetails) model.toArray()[index];
	}

	public void add(ChannelDetails element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(List<ChannelDetails> elements) {
		model.addAll(elements);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(ChannelDetails element) {
		return model.contains(element);
	}

	public ChannelDetails firstElement() {
		return model.first();
	}

	public Iterator<ChannelDetails> iterator() {
		return model.iterator();
	}

	public ChannelDetails lastElement() {
		return model.last();
	}

	public boolean removeElement(ChannelDetails element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}
}
