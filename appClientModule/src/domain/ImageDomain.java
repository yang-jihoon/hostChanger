package src.domain;

import javax.swing.ImageIcon;

import src.HostChanger;

public class ImageDomain {
	private String imgPath = "/img/skin/aire/";
	private ImageIcon delImage;
	private ImageIcon upImage;
	private ImageIcon downImage;
	private ImageIcon infoImage;
	private ImageIcon openImage;
	private ImageIcon saveImage;
	private ImageIcon exitImage;
	private ImageIcon showImage;
	private ImageIcon enableImage;
	private ImageIcon disableImage;
	private ImageIcon addImage;
	
	public ImageDomain() {
		setImage();
	}
	
	public void setImage () {
		this.delImage = new ImageIcon(HostChanger.class.getResource(imgPath+"del.png"));
		this.upImage = new ImageIcon(HostChanger.class.getResource(imgPath+"up.png"));
		this.downImage = new ImageIcon(HostChanger.class.getResource(imgPath+"down.png"));
		this.infoImage = new ImageIcon(HostChanger.class.getResource(imgPath+"info.png"));
		this.openImage = new ImageIcon(HostChanger.class.getResource(imgPath+"open.png")); 
		this.saveImage = new ImageIcon(HostChanger.class.getResource(imgPath+"save.png"));
		this.exitImage = new ImageIcon(HostChanger.class.getResource(imgPath+"exit.png"));
		this.showImage = new ImageIcon(HostChanger.class.getResource(imgPath+"show.png"));
		this.enableImage = new ImageIcon(HostChanger.class.getResource(imgPath+"enable.png"));
		this.disableImage = new ImageIcon(HostChanger.class.getResource(imgPath+"disable.png"));
		this.addImage = new ImageIcon(HostChanger.class.getResource(imgPath+"add.png"));
	}
	
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public ImageIcon getDelImage() {
		return this.delImage;
	}
	public ImageIcon getUpImage() {
		return this.upImage;
	}
	public ImageIcon getDownImage() {
		return this.downImage;
	}
	public ImageIcon getInfoImage() {
		return this.infoImage;
	}
	public ImageIcon getOpenImage() {
		return this.openImage;
	}
	public ImageIcon getSaveImage() {
		return this.saveImage;
	}
	public ImageIcon getExitImage() {
		return this.exitImage;
	}
	public ImageIcon getShowImage() {
		return this.showImage;
	}
	public ImageIcon getEnableImage() {
		return this.enableImage;
	}
	public ImageIcon getDisableImage() {
		return this.disableImage;
	}
	public ImageIcon getAddImage() {
		return this.addImage;
	}
	
}
