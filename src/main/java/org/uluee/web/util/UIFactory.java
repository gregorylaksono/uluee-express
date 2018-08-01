package org.uluee.web.util;

import java.util.Collection;

import org.uluee.web.Uluee_expressUI;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UIFactory {

	
	public static enum LayoutType{
		VERTICAL, HORIZONTAL
	}
	public static enum SizeType{
		FULL, UNDEFINED
	}
	
	public static enum ButtonSize{
		SMALL, NORMAL, TINY
	}
	
	public static enum ButtonStyle{
		FRIENDLY, PRIMARY, NORMAL, DANGER, BORDERLESS, QUIET
	}
	
	public static Button createButton(ButtonSize buttonsize, ButtonStyle style, String caption){
		Button button = new Button(caption);
		if(buttonsize.equals(ButtonSize.SMALL)){
			button.addStyleName(ValoTheme.BUTTON_SMALL);
		}else if(buttonsize.equals(ButtonSize.TINY)){
			button.addStyleName(ValoTheme.BUTTON_TINY);
		}
		
		if(style.equals(ButtonStyle.FRIENDLY)){
			button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		}
		else if(style.equals(ButtonStyle.DANGER)){
			button.addStyleName(ValoTheme.BUTTON_DANGER);
		}
		else if(style.equals(ButtonStyle.BORDERLESS)){
			button.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		}
		else if(style.equals(ButtonStyle.QUIET)){
			button.addStyleName(ValoTheme.BUTTON_QUIET);
		}
		
		return button;
	}
	
//	public static void addWindow(Component content,boolean isExpandable, boolean isDragable, boolean isCloseable, boolean isModal) {
//		Window w = new Window();
//		w.setContent(content);
//		w.setDraggable(isDragable);
//		w.setClosable(isCloseable);
//		w.setModal(isModal);
//		w.setResizable(false);
//		
//		((Uluee_expressUI)UI.getCurrent()).addWindow(w);
//	}
//	
	public static  AbstractOrderedLayout createLayout(LayoutType layoutType, SizeType sizeType, MarginInfo margin, Boolean isSpacing){
		AbstractOrderedLayout layout = null;
		if(layoutType.equals(LayoutType.VERTICAL)){
			layout = new VerticalLayout();
		}else{
			layout = new HorizontalLayout();
		}
		
		
		
		layout.setSpacing(isSpacing);
		
		if(sizeType.equals(SizeType.FULL)){
			layout.setSizeFull();
		}else{
			layout.setSizeUndefined();
		}
		
		if(margin == null){
			layout.setMargin(false);
		}
		else {
			layout.setMargin(margin);
		}
		
		
		return layout;
	}

//	public static void closeAllWindow() {
//		Collection<Window> windows = UI.getCurrent().getWindows();
//		for(Window w: windows) {
//			UI.getCurrent().removeWindow(w);
//		}
//		
//	}
	
	
}
