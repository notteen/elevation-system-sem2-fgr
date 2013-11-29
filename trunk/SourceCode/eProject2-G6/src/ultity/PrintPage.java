package ultity;

import java.awt.*;
import javax.swing.*;
import java.awt.print.*;

public class PrintPage implements Printable {
    private final Component componentToBePrinted;

    public static void printComponent(Component c) {
        new PrintPage(c).print();
    }

    public PrintPage(Component componentToBePrinted) {
        this.componentToBePrinted = componentToBePrinted;
    }

    public void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        if (printJob.printDialog()){
            try {
                printJob.print();
            } catch(PrinterException pe) {
                JOptionPane.showMessageDialog(componentToBePrinted, "Error while print!");
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return(NO_SUCH_PAGE);
        } 
        else {
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            disableDoubleBuffering(componentToBePrinted);
            componentToBePrinted.paint(g2d);
            enableDoubleBuffering(componentToBePrinted);
            return(PAGE_EXISTS);
        }
    }

    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
}