package login.Register.loginRegister.Util;

import com.github.luben.zstd.Zstd;
import com.github.luben.zstd.ZstdException;

public class ZstdUtil {
    //    public static byte[] compressImage(byte[] input) {
//        return Zstd.compress(input);
//    }
    public static byte[] compressImage(byte[] input) {
        int compressionLevel = 22; // 1 (fastest, low compression) to 22 (slowest, highest compression)
        return Zstd.compress(input, compressionLevel);
    }

    public static byte[] decompressImage(byte[] compressed) {
        try {
            long decompressedSize = Zstd.decompressedSize(compressed);

            if (decompressedSize <= 0 || decompressedSize > Integer.MAX_VALUE) {
                throw new RuntimeException("Invalid decompressed size: " + decompressedSize);
            }

            //System.out.println("🔹 Expected Decompressed Size: " + decompressedSize);

            byte[] decompressed = Zstd.decompress(compressed, (int) decompressedSize);

            //System.out.println("🔹 Actual Decompressed Size: " + decompressed.length);

            return decompressed;
        } catch (ZstdException e) {
            throw new RuntimeException("Decompression failed: " + e.getMessage());
        }
    }

}

