package mk.ukim.finki.finkit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import mk.ukim.finki.finkit.service.ImageService;

@Controller
public class ImageResolvingController {

	@Autowired
	private ImageService imageService;
	
	@RequestMapping("resolveImage/{imageId}/**")
	public ResponseEntity<byte[]> resolveImage(@PathVariable Long imageId) {
		
		byte[] image = imageService.getImage(imageId);
		
		HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);
		
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(image, headers, HttpStatus.OK);
		
		return responseEntity;
	}
	
}
