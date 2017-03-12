package mk.ukim.finki.finkit.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.ukim.finki.finkit.model.data.StaticResource;
import mk.ukim.finki.finkit.repositories.StaticResourceRepository;

@Service
public class ImageService {
	
	@Autowired
	private StaticResourceRepository resourceRepository;
	
	public byte[] getImage(Long imageId) {
		StaticResource resource = resourceRepository.findOne(imageId);
		return resource.getContent();
	}

	public static String resolveUrlFromId(Long imageId) {
		long time = new Date().getTime();
		return String.format("/resolveImage/%s/%s", imageId, time);
	}
}
