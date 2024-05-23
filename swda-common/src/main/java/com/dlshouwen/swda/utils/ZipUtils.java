package com.dlshouwen.swda.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import cn.hutool.core.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zip utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ZipUtils {

	//	logger
	private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * zip
	 * @param text
	 * @return result
	 */
	public static String zip(String text) {
		if (text == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zos = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zos = new ZipOutputStream(out);
			zos.putNextEntry(new ZipEntry("0"));
			zos.write(text.getBytes());
			zos.closeEntry();
			compressed = out.toByteArray();
			compressedStr = Base64.encode(compressed);
		} catch (IOException e) {
			logger.error("error", e);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					logger.error("error", e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("error", e);
				}
			}
		}
		return compressedStr;
	}

	/**
	 * unzip
	 * @param compressed
	 * @return result
	 */
	public static String unzip(String compressed) {
		if (compressed == null) {
			return null;
		}
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			byte[] compressedBytes = Base64.decode(compressed);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressedBytes);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
					logger.error("error", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("error", e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("error", e);
				}
			}
		}
		return decompressed;
	}

}