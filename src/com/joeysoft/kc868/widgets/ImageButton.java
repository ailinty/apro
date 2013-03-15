package com.joeysoft.kc868.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import com.joeysoft.kc868.ui.Colors;

public class ImageButton extends Composite implements DisposeListener, PaintListener, ControlListener{
	private Image image, imageUp, imageOn;
	
	private int style;
	// 是否按下
	private boolean up;
	// 鼠标是否进入控件区域
	private boolean enter;
	// 文字
	private String text;
	// 是否显示文字
	private boolean showText;
	// 文字的水平位置
	private int textOrientation;
	// 文字的垂直位置
	private int textVOrientation;
	// 鼠标进入控件范围时显示的文字颜色
	private Color mouseTrackColor;
	// 客户区大小
	private Rectangle clientArea;
	// 图片区
	private Rectangle imageBound;
	// 图像和文字的外围矩形
	private Rectangle totalBound;
	//文字颜色
	private Color textColor;
	//文字字体
	private Font font;
	
	public ImageButton(Composite parent, int style, String text, Image image, Image imageUp, Image imageOn) {
		super(parent, style);
		this.style = style;
		
		init(style, text, image, imageUp, imageOn);
		
		addDisposeListener(this);
		addPaintListener(this);
		addControlListener(this);
		
		super.addMouseListener(new ImageButtonMouseListener());
		super.addMouseTrackListener(new ImageButtonMouseTrackListener());
	}

	private void init(int _style, String _text, Image _image, Image _imageUp, Image _imageOn) {
		// 初始化image
        this.image = _image;
        this.imageUp = _imageUp;
        this.imageOn = _imageOn;
        
        if(_image != null)
            imageBound = _image.getBounds();
        else 
        	imageBound = new Rectangle(0, 0, 0, 0);
        
        checkStyle(_style);
        
        up = true;
        showText = true;
        if(_text == null)
		    _text = "";
		this.text = _text;
		
		textColor = Colors.WHITE;
		
		totalBound = new Rectangle(1, 1, 0, 0);
		refreshTotalBound();
	}
	
	/**
     * 检查style
     * 
     * @param _style
     */
    private void checkStyle(int _style) {
		// 检查style，只允许下列值
		this.style = _style & (SWT.LEFT | SWT.RIGHT | SWT.CENTER | SWT.TOP | SWT.BOTTOM);
		// 检查style看看文本位置设置了没有，没有则用缺省的right
	    textOrientation = this.style & (SWT.LEFT | SWT.RIGHT | SWT.CENTER);
	    if(textOrientation == 0) 
	    	textOrientation = SWT.CENTER;
	    
	    textVOrientation = this.style & (SWT.TOP | SWT.BOTTOM | SWT.CENTER);
	    if(textVOrientation == 0) 
	    	textVOrientation = SWT.CENTER;
    }
    
    /**
     * 设置是否显示文字
     * @param show true表示显示文字
     */
    public void setShowText(boolean show) {
    	if(showText != show) {
    		showText = show;
    		redraw();
    	}
    }
    
    /**
     * @param text The text to set.
     */
    public void setText(String text) {
        if(text == null)
            text = "";
        else
        	this.text = text;
        if(showText) {
            // 重新设置image和text的总体外围矩形
           // refreshTotalBound();
            // 重画
	        redraw();            
        }
    }
    
    /**
     * @return
     * 		可用高度
     */
    private int getAvailableHeight() {
    	if(clientArea == null)
    		clientArea = getClientArea();
    	return clientArea.height - 4;
	}
    
    // 重新设置image和text的总体外围矩形
    private void refreshTotalBound() {
    	Point extent = getTotalSize();
        totalBound.width = extent.x;
        totalBound.height = extent.y;
		clientArea = getClientArea();
		if(textOrientation == SWT.LEFT)
			totalBound.x = clientArea.width - totalBound.width;
		else
			totalBound.x = 2;
    }
    
    /**
     * 设置鼠标进入控件范围时显示的文字颜色
     * @param color 鼠标进入控件范围时显示的文字颜色
     */
    public void setMouseTrackColor(Color color) {
        this.mouseTrackColor = color;
    }
    
    /**
     * 设置文字颜色
     * @param color 文字颜色
     */
    public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
    
    /**
     * 设置文字字体
     * @param font 文字字体
     */
	public void setFont(Font font) {
		this.font = font;
	}
	/**
     * @return Returns the text.
     */
    public String getText() {
        return text;
    }
    
    /**
     * 得到image和text的总大小
     * 
     * @return
     * 		组件大小
     */
    private Point getTotalSize() {
        Point size = new Point(0, 0);
        size.y = getAvailableHeight();
       // size.x = (image == null) ? 0 : size.y;
        
        GC gc = new GC(this);
        if(text.length() > 0 && showText) {
            Point e = gc.textExtent(text, SWT.DRAW_TRANSPARENT);
            // 在文字两旁留5个象素的空隙
            size.x += e.x + 10;
        } 
        size.y = Math.max(size.y, gc.getFontMetrics().getHeight() + 4);
        gc.dispose();
        return size;
    }
        
    /**
     * 计算截短的字符串，从中间开始不断减少，中间填省略号，直到宽度合适未知
     * 
     * @param gc
     * @param t
     * @param width
     * @return
     */
    protected String shortenText(GC gc, String t, int width) {
        if(t == null)
            return null;
        int w = gc.textExtent("...").x;
        int l = t.length();
        int pivot = l / 2;
        int s = pivot;
        for(int e = pivot + 1; s >= 0 && e < l; e++) {
            String s1 = t.substring(0, s);
            String s2 = t.substring(e, l);
            int l1 = gc.textExtent(s1).x;
            int l2 = gc.textExtent(s2).x;
            if(l1 + w + l2 < width) {
                t = s1 + "..." + s2;
                break;
            }
            s--;
        }

        return t;
    }
    
    /**
     * 缺省的鼠标进入进出动作
     * 
     * @author joey
     */
    private class ImageButtonMouseTrackListener extends MouseTrackAdapter {        
        public void mouseEnter(MouseEvent e) {
        	if(imageUp != null){
        		enter = true;
                redraw();
        	}
        }

        public void mouseExit(MouseEvent e) {
        	if(imageUp != null){
        		enter = false;
                redraw();
        	}
        }
    }
    
    /**
     * 缺省的鼠标动作
     * 
     * @author joey
     */
    private class ImageButtonMouseListener extends MouseAdapter {
        public void mouseDown(MouseEvent e) {
        	if(imageOn != null){
	            up = false;
	            redraw();
        	}
        }
        
        public void mouseUp(MouseEvent e) {
        	if(imageUp != null){
        		up = true;
                redraw(); 
            	forceFocus();
        	}
        }
    }
    
	@Override
	public void controlMoved(ControlEvent e) {
		
	}

	@Override
	public void controlResized(ControlEvent e) {
		refreshTotalBound();
	}

	@Override
	public void paintControl(PaintEvent event) {
		GC gc = event.gc;
		if(image != null) {
			if(imageOn == null && imageUp == null){
				gc.drawImage(image, 0, 0);
			}else{
				if(!up && imageOn != null) {
		        	gc.drawImage(imageOn, 0, 0);
		        } else if(enter && imageUp != null) {
		        	gc.drawImage(imageUp, 0, 0);
		        } else {
		        	gc.drawImage(image, 0, 0);
		        }
			}
        }
		
		// 画文字
        if(!showText)        	
        	return;
        
        if(font != null){
        	gc.setFont(font);
        }
        
        // 计算可用宽度，如果宽度不够，截短文字
        boolean shorten = false;
        String t = text;
        int availableWidth = clientArea.width - 2;
        Point extent = getTotalSize();
        if(extent.x > availableWidth){
        	shorten = true;
        	System.out.println(extent.x + "  "+availableWidth);
        }
            
        // 如果需要截短文字，得到截短后的文字
        if(shorten) 
            t = shortenText(gc, t, availableWidth);

        int h = gc.getFontMetrics().getHeight();
        int w = gc.textExtent(t, SWT.DRAW_TRANSPARENT).x;

        // 设置文本前景色，如果设置了鼠标hover时的文本色，则使用
        if(!isEnabled())
        	gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
        else if(enter && mouseTrackColor != null)
        	gc.setForeground(mouseTrackColor);
        else
        	gc.setForeground(textColor);
        
        int textY = (clientArea.height - w) / 2, textX = (clientArea.width - w) / 2;
        
        switch(textOrientation) {
        	case SWT.RIGHT:
        		textX = clientArea.width - 1 - 5 - w;
        		break;
        	case SWT.LEFT:
        		textX = 1 + 5;
        		break;
        	case SWT.CENTER:
        		textX = (clientArea.width - w) / 2;
        		break;
        }
        
        switch(textVOrientation) {
	    	case SWT.TOP:
	    		textY = 1 + 5;
	    		break;
	    	case SWT.BOTTOM:
	    		textY = clientArea.height/2 > 2 * h ? clientArea.height - h - h + h/3:clientArea.height - h;
	    		break;
	    	case SWT.CENTER:
	    		textY = (clientArea.height - h) / 2;
	    		break;
	    }
        
        gc.drawString(t, textX, textY, true);
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		image = imageUp = imageOn = null;
		text = null;
		mouseTrackColor = null;
	}

	public void setImage(Image image) {
		this.image = image;
		redraw();
	}
}
