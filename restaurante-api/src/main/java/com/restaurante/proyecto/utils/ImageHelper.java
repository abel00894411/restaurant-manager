package com.restaurante.proyecto.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public final class ImageHelper {

	public static final String extension=".jpg";
	
	public static void crearImgen(String fileName, String filePath, byte[] imagenArray) {
		String image = filePath.concat(fileName.concat(extension));
		try (FileOutputStream fos = new FileOutputStream(image)) {

			fos.write(imagenArray);
			fos.flush();
			System.out.println("simon se guardo con exito en: " + image);
		} catch (Exception e) {
			System.out.println("Error al escribir pa: " + e.getMessage());
			eliminarImagen(fileName, filePath);
			throw new RuntimeException("no se pudo crear la imagen");
		}

	}

	/**
	 * 
	 * @param fil El nombre del archivo
	 * @param path La ruta donde se encuentra el archivo 
	 * @return
	 */
	public static byte[] obtenerImagenBytes(String fil, String path) {

		String filePath = path + fil +extension;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = fis.read(buf)) != -1) {
				bos.write(buf, 0, bytesRead);
			}
			byte[] bytes = bos.toByteArray();
			fis.close();
			bos.close();
			return bytes;
		} catch (IOException e) {
			
			return new byte[0];
		}
	}

	public static void eliminarImagen(String fil, String path) {
		String filePath = path+fil+extension;
		
		System.out.println("\n\nruta a elimir archivo: "+filePath);
		
		File file = new File(filePath);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("La imagen se eliminó con éxito.");
			} else {
				System.out.println("No se pudo eliminar la imagen.");
			}
		} else {
			System.out.println("La imagen no existe en la ubicación especificada.");
		}
	}

}
