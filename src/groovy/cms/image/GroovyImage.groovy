package cms.image
  
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;

/**
 * A batch image manipulation utility
 *
 * A wrote this script just to get groovy, batch manipulate images in about
 * 240 lines of code (without this comment)!!!.
 *
 * commands:
 * values ending with '%' means size relative to image size.
 * values ending with 'px' means values in absolute pixels.
 * values without postfix use default notation.
 *
 * expressions:
 * scale(width,height)      * height is optional(use width) e.g: scale(50%) == scale(50%,50%)
 * fit(width,height)        * relative scale the image until if fits (defaut as scale)
 *                          * bounds of the given box, usefull for generating of thumbnails.
 * rotate(degrees,x,y)      * the rotation position x and y are optional (default is 50%)
 *
 * TODO: move(x,y)          * move the image within its own bounds (can be done with margin)
 *                          * y is optional(same height)
 * TODO: color(type)        * color transformation
 * TODO: shear(degrees,x,y) * x and y is optional
 * margin(x,y,x2,y2)        * add margins to image (resize image canvas), this operation can't 
 *                          * be used on a headless environment.
 * parameters:
 * -d                       * working directory (default current directory)
 * -e                       * execute expressions from command line.
 * -f                       * execute expressions from file.
 * -p                       * file mathing pattern default is \.png|\.jpg
 * -q                       * output file pattern can use {0} .. {9} 
 *                          * backreferences from the input pattern. default: output/{0}
 * -h                       * help, nothing special (maybe this doc using heredoc)
 *
 * Example generate thumbnails(take *.png from images fit them in a 100X100 box,
 * add 10px margin, put them in the thumbnail dir.)
 *
 * $ groovy image.groovy -d images -e "fit(100px,100px) margin(5)" -p "(.*)\.png" -q "thumbnail/{1}.png"
 *
 * @author Philip Van Bogaert alias tbone
 */
class GroovyImage {
    File srcDir = new File(".");

    def operations = [];

    def pattern = ~".*(\\.png|\\.jpg)";
    def outputPattern = "output/{0}";

    void addOperation(def command) {

    	def matcher = command =~ "([a-z]+)\\((.*)\\).*";
        matcher.find();

        def method = matcher.group(1);
        def args = matcher.group(2).split(",").toList();

        switch(method) {
            case "scale": // vertical,horizontal
                operations.add(["parseAndScale",argsLength(args,2)]);
                break;

            case "rotate": // degrees,x,y
                operations.add(["parseAndRotate",argsLength(args,3)]);
                break;

            case "margin": // left,top,right,bottom
                operations.add(["parseAndMargin",argsLength(args,4)]);
                break;

            case "fit": // width,height
                operations.add(["parseAndFit",argsLength(args,2)]);
                break;
        }
    }

    BufferedImage parseAndRotate(def image,def degrees,def x,def y) {
    	def parsedRadians = 0;
        try {
            parsedRadians = Math.toRadians(Double.parseDouble(degrees));
        }
        catch(NumberFormatException except) {
        }

        def parsedX = parseValue(x,image.width,true,"50%");
        def parsedY = parseValue(y,image.height,true,parsedX);

        return rotate(image,parsedRadians,parsedX,parsedY);
    }

    BufferedImage rotate(def image,def radians,def x,def y) {
    	def transform = new AffineTransform();
        transform.rotate(radians,x,y);
        def op = new AffineTransformOp(transform,AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image,null);
    }

    BufferedImage parseAndScale(def image,def horizontal,def vertical) {
    	def parsedHorizontal =  parseValue(horizontal,image.width,false,"100%");
    	def parsedVertical =  parseValue(vertical,image.height,false,parsedHorizontal);
        return scale(image,parsedHorizontal,parsedVertical);
    }

    BufferedImage scale(def image,def horizontal,def vertical) {
    	/*def transform = new AffineTransform();
        transform.scale(horizontal,vertical);
        def op = new AffineTransformOp(transform, new RenderingHints(
                [(RenderingHints.KEY_INTERPOLATION):RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                 (RenderingHints.KEY_RENDERING): RenderingHints.VALUE_RENDER_QUALITY]))*/
        //return op.filter(image,null);

      getScaledInstance(image, (int) (horizontal * image.width), (int) (vertical * image.height))
    }



    BufferedImage parseAndMargin(def image,def left,def top,def right,def bottom) {
    	def parsedLeft = parseValue(left,image.width,true,"0px");
        def parsedTop =  parseValue(top,image.height,true,parsedLeft);
        def parsedRight = parseValue(right,image.width,true,parsedLeft);
        def parsedBottom = parseValue(bottom,image.height,true,parsedTop);
        return margin(image,parsedLeft,parsedTop,parsedRight,parsedBottom);
    }

    BufferedImage margin(def image,def left,def top,def right,def bottom) {
    	def width = left + image.width + right;
    	def height = top + image.height + bottom;
    	def newImage = new BufferedImage(width.intValue(), height.intValue(),BufferedImage.TYPE_INT_RGB);
    	//set the background
        println "Setting background or margin image"
        setBackground(newImage, Color.WHITE)
        // createGraphics() needs a display, find workaround.
        def graph = newImage.createGraphics();
        graph.drawImage(image,new AffineTransform(1.0d,0.0d,0.0d,1.0d,left,top),null);
        return newImage;
    }

    BufferedImage parseAndFit(def image,def width,def height) {
    	def parsedWidth = parseValue(width,image.width,true,"100%");
    	def parsedHeight = parseValue(height,image.height,true,parsedWidth);

    	def imageRatio = image.width / image.height;
    	def fitRatio = parsedWidth / parsedHeight;

        if(fitRatio < imageRatio) {
            parsedHeight = image.height * (parsedWidth/image.width);
        } else {
            parsedWidth = image.width * (parsedHeight/image.height);
        }

        println "Image width is ${width}, parsedWidth = ${parsedWidth}"
        
        def sideMargin = (parseValue(width,image.width,true,"100%") - parsedWidth) / 2
        def topBottomMargin = (parseValue(height,image.height,true,"100%") - parsedHeight) / 2
        return margin(parseAndScale(image,parsedWidth+"px",parsedHeight+"px"), sideMargin, topBottomMargin, sideMargin, topBottomMargin) 
    }

    static void setBackground(BufferedImage image, Paint paint) {
        Graphics2D g = image.createGraphics();
        g.setPaint(paint);
        g.fillRect(0,0,image.getWidth(), image.getHeight());
        g.dispose();
    }
    
    BufferedImage manipulate(def image) {
        for(operation in operations) {
        	println "Operation = " + operation[0]
            image = "${operation[0]}"([image] + operation[1]);
        }
        return image;
    }

    void batch() {
    	println "Scanning images"
        def images = getImages();
        println "Starting manipulation"
        for(imageMap in images) {
        	def image = ImageIO.read(imageMap.file)
            imageMap.image = manipulate(image);
            storeImage(imageMap);
        }
    }


     Object getImages() {
        def imageMaps = [];

        for(i in srcDir.listFiles()) {
            if(!i.isDirectory()) {
                def subpath = i.path;
                if(subpath.startsWith(srcDir.path)) {
                    subpath = subpath.substring(srcDir.path.length());
                }
                def matcher = subpath =~ pattern;
                if(matcher.find()) {
                    imageMaps.add(["file":i,"matcher":matcher]);
                }
            }
        }
        //imageMaps.each({it["image"] = ImageIO.read(it["file"]); });
        return imageMaps;
    }


    void storeImage(def imageMap) {
        def groupIndex = 0;
        def name = outputPattern;
        def matcher = imageMap.matcher;
        while(groupIndex <= matcher.groupCount()) {
            name = name.replaceAll("\\{${groupIndex}\\}",matcher.group(groupIndex++));
        }
        def type = name.substring(name.lastIndexOf(".")+1,name.length());
        def file = new File(srcDir,name);
        file.mkdirs();
        ImageIO.write(imageMap.image,type,file);
    }

    public static void main(def args) {
    	def argList = args.toList();
    	def script ='';
    	def groovyImage = new GroovyImage();

        // command line parsing bit, NOTE: -h does System.exit(2)
        def argAndClosure = ['-d':{groovyImage.srcDir = new File(it)},
                         '-q':{groovyImage.outputPattern = it},
                         '-p':{groovyImage.pattern = it},
                         '-h':{groovyImage.help()}];
        // parse non-conditional arguments
        parseMultipleCommandArgs(argList,argAndClosure);

        // expression,file,nothing
        if(!parseCommandArg(argList,'-e', {script = it}))  {
            parseCommandArg(argList,'-f',{script = new File(it).text});
        }

        println "Gathering operations"
        // execution bit
        def commands = script =~ "([a-z]{1,}\\([^)]*\\))";
        while(commands.find()) {
            groovyImage.addOperation(commands.group(1));
        }
        println "Starting batch execution"
        groovyImage.batch();

    }

    static boolean parseCommandArg(def args,def arg,def closure) {
    	def index = args.indexOf(arg);

        if(index != -1 && index + 1 < args.size()) {
        	println "Executing " + args[index + 1]
            closure.call(args[index + 1]);
            return true;
        } else {
            return false;
        }
    }

    static void parseMultipleCommandArgs(def args,def argAndClosureMap) {
        for(argAndClosure in argAndClosureMap) {
            parseCommandArg(args,argAndClosure.key,argAndClosure.value);
        }
    }

    void help() {
        println('usage: groovy image.groovy -i <inputDir> -o <outputDir> -e "<expressions>"');
        System.exit(2);
    }

    /**
     * absolute true  -> returns pixels.
     *          false -> returns relative decimal (e.g 1.0).
     */
    Number parseValue(def value, def size,def absolute,def defaultValue="0") {
    	 def pattern = "(-?[0-9]+\\.?[0-9]*)(.*)";
    	 def matcher = value =~ pattern;
        if(!matcher.find()) {
            matcher = defaultValue =~ pattern;
            matcher.find();
        }

        def decimalValue = Double.parseDouble(matcher.group(1));
        def type = matcher.group(2);

        if(absolute) { // pixels
            switch(type)  {
                case "%":
                    return (int) size * (decimalValue / 100);
                case "px":
                default:
                return (int) decimalValue;
            }
        }
        else { // scale
            switch(type) {
                case "px":
                    return decimalValue / size;
                case "%":
                    return decimalValue / 100;
                default:
                    return decimalValue;
            }
        }
    }

    Object argsLength(def args,def length) {
        if(args.size() < length) {
            while(args.size() < length) {
                args.add("");
            }
        } else {
            args = args.subList(0,length);
        }
        return args;
    }



  public static Image getScaledInstance(BufferedImage img,
                                       int targetWidth,
                                       int targetHeight)
        {
            // REMIND: This only works for opaque images...

            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            int iw = img.getWidth();
            int ih = img.getHeight();

            Object hint = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
            int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

            // First get down to no more than 2x in W & H
            while (iw > targetWidth*2 || ih > targetHeight*2) {
                iw = (iw > targetWidth*2) ? iw/2 : iw;
                ih = (ih > targetHeight*2) ? ih/2 : ih;
                img = scaleImage(img, type, hint, iw, ih);
            }

            // REMIND: Conservative approach:
            // first get W right, then worry about H

            // If still too wide - do a horizontal trilinear blend
            // of img and a half-width img
            if (iw > targetWidth) {
                int iw2 = iw/2;
                BufferedImage img2 = scaleImage(img, type, hint, iw2, ih);
                if (iw2 < targetWidth) {
                    img = scaleImage(img, type, hint, targetWidth, ih);
                    img2 = scaleImage(img2, type, hint, targetWidth, ih);
                    interp(img2, img, iw-targetWidth, targetWidth-iw2);
                }
                img = img2;
                iw = targetWidth;
            }
            // iw should now be targetWidth or smaller

            // If still too tall - do a vertical trilinear blend
            // of img and a half-height img
            if (ih > targetHeight) {
                int ih2 = ih/2;
                BufferedImage img2 = scaleImage(img, type, hint, iw, ih2);
                if (ih2 < targetHeight) {
                    img = scaleImage(img, type, hint, iw, targetHeight);
                    img2 = scaleImage(img2, type, hint, iw, targetHeight);
                    interp(img2, img, ih-targetHeight, targetHeight-ih2);
                }
                img = img2;
                ih = targetHeight;
            }
            // ih should now be targetHeight or smaller

            // If we are too small, then it was probably because one of
            // the dimensions was too small from the start.
            if (iw < targetWidth && ih < targetHeight) {
                img = scaleImage(img, type, hint, targetWidth, targetHeight);
            }

            return img;
        }

        private static BufferedImage scaleImage(BufferedImage orig,
                                         int type,
                                         Object hint,
                                         int w, int h)
        {
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON)
            g2.drawImage(orig, 0, 0, w, h, null);
            g2.dispose();
            return tmp;
        }

        private static void interp(BufferedImage img1,
                            BufferedImage img2,
                            int weight1,
                            int weight2)
        {
            float alpha = weight1;
            alpha /= (weight1 + weight2);
            Graphics2D g2 = img1.createGraphics();
            g2.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.drawImage(img2, 0, 0, null);
            g2.dispose();
        }

}