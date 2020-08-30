/* *****************************************************************************
 *  Name: Mohammad Alqudah
 *  NetID: malqudah
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 7: Seam Carving


/* *****************************************************************************
 *  Describe concisely your algorithm to find a horizontal (or vertical)
 *  seam.
 **************************************************************************** */
To find a vertical seam, i reimplemented the Topological Shortest Paths
Algorithm, referenced from AcyclicSP.java. i represented the pixels as an
implicit graph of their energies. the distTo array was comprised
of the energy value leading to a given point, referenced from the energy array
values calculated in the energy method. given a "vertex", relax the edges
pointing from it as seen in AcyclicSP and then update the minimum distance
path accordingly as well as the edgeTo array. For the horizontal seam,
i transposed the image and checked for the vertical seam, then transposed it
back.


/* *****************************************************************************
 *  Describe what makes an image suitable to the seam-carving approach
 *  (in terms of preserving the content and structure of the original
 *  image, without introducing visual artifacts). Describe an image that
 *  would not work well.
 **************************************************************************** */
An image that is suitable for the seam carving approach is probably one with
a very distinct subject, and therefore lot of background solid colors.
that way it is very easy to distinguish between the high and low energy pixels,
as it is much easier to narrow down the pixels with low effect on the image.
an image with no distinct subject that has important images plastered all over
it would not be suitable for the seam carving approach, as some of the important
parts fo the picture could be cut out.


/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column and one row (i.e., one call each to
 *  findVerticalSeam(), removeVerticalSeam(), findHorizontalSeam(), and
 *  removeHorizontalSeam()). Use a "doubling" hypothesis, where you
 *  successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant)
 W = 1000
 multiplicative factor (for H) = 2


 H           time (seconds)      ratio       log ratio
------------------------------------------------------
98                  0.286
196                 0.322       1.13            0.17
391                 0.401       1.25            0.32
782                 0.596       1.49            0.57
1,563               0.891       1.49            0.58
3,125               1.57        1.76            0.82
6,250               2.874       1.83            0.87
12,500              5.111       1.78            0.83
25,000              10.591      2.07            1.05
50,000              21.066      1.99            0.99

converges to 0.99

(keep H constant)
 H = 1000
 multiplicative factor (for W) = 2

 W           time (seconds)      ratio       log ratio
------------------------------------------------------
118             0.247
235             0.38            1.54            0.62
469             0.47            1.24            0.31
938             0.717           1.53            0.61
1,875           1.053           1.47            0.55
3,750           1.706           1.62            0.70
7,500           3.027           1.77            0.83
15,000          6.232           2.05            1.04
30,000          11.844          1.90            0.93
60,000          22.945          1.94            0.95

converges to 0.95


/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^5.1 * H^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */

this is found by picking largest values of W and H respectively
       and setting F(H, W) = a * H^0.99 * W^0.95
       0.99 and 0.95 and the log ratio estimates for H and W respectively
       i picked a value, F(n) and set the equation equal to that and solved
       for a.
       F(H, W) = a * 50,000^0.99 * 60,000^0.95
       a = 1.4 * 10^-8


Running time (in seconds) to find and remove one horizontal seam and one
vertical seam, as a function of both W and H:


    ~
       1.4 * 10^-8 * H^0.99 * W^0.95




/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */
Tim and Lisa

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
