/* *****************************************************************************
 *  Name:    Mohammad Alqudah
 *  NetID:   malqudah
 *  Precept: P05
 *
 *
 *  Description:  Creates a content aware image resizing program which finds
 * a vertical and horizontal seam through re implementing the
 *  topological sort shortest paths algorithm. given an image, each pixel has
 * an energy which determines its relevance to the image. a vertical seam is a
 * column of pixels that when removed, won't impact the compact of the image
 * significantly. Similarly, a horizontal seam is a row of the same definition.
 * This program detects the vertical and horizontal seams, and removes them
 * from a given image.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class SeamCarver {


    // overall picture
    private Picture pic;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Constructor input is null");
        }
        pic = new Picture(picture);
    }


    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // helper method for the energy argument checks
    // energy at column x and row y
    private void energyExcp(int x, int y) {
        if (x < 0 || x > pic.width() - 1 || y < 0 || y > pic.height() - 1) {
            throw new IllegalArgumentException("coordinate is outside range");
        }
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        energyExcp(x, y);
        return helpEnergy(x, y);
    }

    // helper method to calculate the energy, splits work with gradients method
    private double helpEnergy(int x, int y) {

        Color left;
        Color right;
        Color up;
        Color down;
        if (x == 0) {
            left = pic.get(width() - 1, y);
        }
        else {
            left = pic.get(x - 1, y);
        }
        if (x == pic.width() - 1) {
            right = pic.get(0, y);
        }
        else {
            right = pic.get(x + 1, y);
        }
        double xGrad = gradients(left, right);

        if (y == 0) {
            up = pic.get(x, pic.height() - 1);
        }
        else {
            up = pic.get(x, y - 1);
        }

        if (y == pic.height() - 1) {
            down = pic.get(x, 0);
        }
        else {
            down = pic.get(x, y + 1);
        }
        double yGrad = gradients(up, down);

        return Math.sqrt(xGrad + yGrad);
    }

    // helper method to complement helpenergy, calculates the gradients of a
    // given pair of colors
    private double gradients(Color c1, Color c2) {
        double red = Math.pow(c1.getRed() - c2.getRed(), 2);
        double green = Math.pow(c1.getGreen() - c2.getGreen(), 2);
        double blue = Math.pow(c1.getBlue() - c2.getBlue(), 2);
        return (red + green + blue);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    // helper method to transpose the picture
    private void transpose() {
        Picture transpose = new Picture(pic.height(), pic.width());
        for (int row = 0; row < pic.height(); row++) {
            for (int col = 0; col < pic.width(); col++) {
                transpose.set(row, col, pic.get(col, row));
            }
        }
        pic = transpose;
    }

    // sequence of indices for vertical seam
    // indices are separate for each row
    public int[] findVerticalSeam() {

        double[][] energyArr = new double[pic.height()][pic.width()];
        double[][] distTo = new double[pic.height()][pic.width()];
        int[][] edgeTo = new int[pic.height()][pic.width()];

        for (int row = 0; row < pic.height(); row++) {
            for (int col = 0; col < pic.width(); col++) {
                energyArr[row][col] = energy(col, row);
                if (row == 0) {
                    distTo[row][col] = energyArr[row][col];
                    edgeTo[row][col] = -1;
                }
                else {
                    distTo[row][col] = Double.POSITIVE_INFINITY;
                    edgeTo[row][col] = -1;
                }
            }
        }

        for (int row = 0; row < pic.height() - 1; row++) {
            for (int col = 0; col < pic.width(); col++) {
                int newRow = row + 1;
                for (int newCol = col - 1; newCol <= col + 1; newCol++) {
                    if (newCol < pic.width() && newCol >= 0) {
                        if (distTo[newRow][newCol] > distTo[row][col] +
                                energyArr[newRow][newCol]) {
                            distTo[newRow][newCol] = distTo[row][col] +
                                    energyArr[newRow][newCol];
                            edgeTo[newRow][newCol] = col;
                        }
                    }
                }
            }
        }

        int[] seam = new int[pic.height()];
        double minDist = Double.POSITIVE_INFINITY;
        int seamVal = -1;

        for (int col = 0; col < pic.width(); col++) {

            if (distTo[pic.height() - 1][col] < minDist) {
                minDist = distTo[pic.height() - 1][col];
                seamVal = col;
            }
        }
        for (int row = pic.height() - 1; row >= 0; row--) {
            seam[row] = seamVal;
            seamVal = edgeTo[row][seamVal];
        }

        return seam;
    }


    // helper method for all of the argument checks for remove horizontal and
    // vertical seams
    private void seamExcp(int[] seam, boolean isVertical) {
        if (seam == null) {
            throw new IllegalArgumentException("Seam is null");
        }
        if (pic.height() <= 1 && !isVertical) {
            throw new IllegalArgumentException("Picture dimensions invalid");
        }
        if (pic.width() <= 1 && isVertical) {
            throw new IllegalArgumentException("Picture dimensions invalid");
        }

        if (seam.length != pic.height() && isVertical) {
            throw new IllegalArgumentException("Seam length invalid");
        }

        if (seam.length != pic.width() && !isVertical) {
            throw new IllegalArgumentException("Seam length invalid");
        }

        int base = seam[0];
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - base) > 1) {
                throw new IllegalArgumentException("Array is not a valid seam");
            }
            if ((seam[i] >= pic.width() || seam[i] < 0) && isVertical) {
                throw new IllegalArgumentException("Array is not a valid seam");
            }
            if ((seam[i] >= pic.height() || seam[i] < 0) && !isVertical) {
                throw new IllegalArgumentException("Array is not a valid seam");
            }
            base = seam[i];
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        seamExcp(seam, false);
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        seamExcp(seam, true);
        Picture removed = new Picture(pic.width() - 1, pic.height());

        for (int row = 0; row < pic.height(); row++) {
            int seamCol = seam[row];
            for (int col = 0; col < seamCol; col++) {
                removed.set(col, row, pic.get(col, row));
            }
            for (int col = seamCol + 1; col < pic.width(); col++) {
                removed.set(col - 1, row, pic.get(col, row));
            }
        }
        pic = removed;
    }

    //  unit testing (required)
    public static void main(String[] args) {
        // Stopwatch stop = new Stopwatch();
        Picture test = SCUtility.randomPicture(235, 1000);
        SeamCarver testOne = new SeamCarver(test);
        test.show();
        StdOut.println("Original Width: " + test.width());
        StdOut.println("Original Height: " + test.height());
        StdOut.println("Energy of a random pixel (2, 3): "
                               + testOne.energy(2, 3));
        testOne.removeVerticalSeam(testOne.findVerticalSeam());
        testOne.removeHorizontalSeam(testOne.findHorizontalSeam());
        StdOut.println("New Width: " + testOne.width());
        StdOut.println("New Height: " + testOne.height());
        testOne.picture().show();
        // StdOut.println("Time elapsed: " + stop.elapsedTime());
    }

}
