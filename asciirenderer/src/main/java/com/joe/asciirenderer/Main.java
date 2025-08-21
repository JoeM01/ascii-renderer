package com.joe.asciirenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Main {
	static int kernelSize = 3;
	public static void main(String[] args) throws Exception {
		SwingWindow window = new SwingWindow(1920, 1080);
    	
    	
    	// IMAGES
    	String image = "cat.jpg";
    	//playImage(image, window);	
    	
    	
    	// GIFS
    	String gif = "video.gif";
    	//playGif(gif, window);
    	
    	// VIDEO
    	String video = ("followtheleader.mp4");
    	playVideo(video, window);
    	
    	// WEBCAM
    	//playWebcam(window);
    }
    
    public static void playImage(String image, SwingWindow window) throws IOException{
    	BufferedImage img = ImageIO.read(new File(image));
    	String ascii = AsciiRenderer.toAscii(img, kernelSize);
    	window.updateFrame(ascii); 	
    }
    
    public static void playGif(String gif, SwingWindow window) throws Exception{
    	Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
		ImageReader reader = readers.next();
		ImageInputStream stream = ImageIO.createImageInputStream(new File(gif));
        reader.setInput(stream, false);
        int numFrames = reader.getNumImages(true);
        
        while (true) { // loop forever
            for (int i = 0; i < numFrames; i++) {
                BufferedImage frame = reader.read(i);
                String ascii = AsciiRenderer.toAscii(frame, kernelSize);
                window.updateFrame(ascii);
                Thread.sleep(100); // frame delay
            
            }
        }

    }
    
   public static void playVideo(String video, SwingWindow window) throws Exception {
	   FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(video);
	   grabber.start();
	   Java2DFrameConverter converter = new Java2DFrameConverter();
	   
	   Frame frame;
	   while((frame = grabber.grab()) != null) {
		  BufferedImage img = converter.convert(frame);
          if (img != null) {	
             String ascii = AsciiRenderer.toAscii(img, kernelSize);
             window.updateFrame(ascii);
          }
          Thread.sleep(33); // ~30fps
	   }
	   grabber.stop();
   }
   
   public static void playWebcam(SwingWindow window) throws Exception {
	   
	   
	   OpenCVFrameGrabber webcam = new OpenCVFrameGrabber(0); // 0 = default camera
	   webcam.start();
	   Java2DFrameConverter converter = new Java2DFrameConverter();

	   Frame frame;
	   while ((frame = webcam.grab()) != null) {
		   BufferedImage img = converter.convert(frame);
		   if (img != null) {
			   String ascii = AsciiRenderer.toAscii(img, kernelSize);
			   window.updateFrame(ascii);
		   }
	   }
	        webcam.stop();
   }
}

