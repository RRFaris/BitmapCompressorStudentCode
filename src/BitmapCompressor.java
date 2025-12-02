/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Ryan
 */
public class BitmapCompressor {
    public static final int BITS_PER_INT = 8;

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {
        int count = 1;

        int previousBit = BinaryStdIn.readInt(1);

        while (!BinaryStdIn.isEmpty()) {
            int bit = BinaryStdIn.readInt(1);

            if (previousBit == bit && count < 255) {
                count++;
            }
            else if (count >= 255) {
                BinaryStdOut.write(count, BITS_PER_INT);
                BinaryStdOut.write(0, BITS_PER_INT);
                count = 1;
            }
            else {
                BinaryStdOut.write(count, BITS_PER_INT);
                previousBit = bit;
                count = 1;
            }
        }
        BinaryStdOut.write(count, BITS_PER_INT);
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        boolean even = true;

        while (!BinaryStdIn.isEmpty()) {
            int numRepetitions = BinaryStdIn.readInt(BITS_PER_INT);
            for (int i = 0; i < numRepetitions; i++) {
                if (even) {
                    BinaryStdOut.write(0, 1);
                } else {
                    BinaryStdOut.write(1,1);
                }
            }
            even = !even;
        }
        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}