package br.com.vestdesk.schedule;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import br.com.vestdesk.domain.Layout;
import br.com.vestdesk.service.LayoutService;
import br.com.vestdesk.util.ImageUtil;

@Component
@EnableScheduling
public class VestdeskSchedule
{

	private final LayoutService layoutService;

	public VestdeskSchedule(LayoutService layoutService)
	{
		this.layoutService = layoutService;
	}

	// @Scheduled(cron = "50 * * * * *")
	public void optimizeLayoutImages()
	{
		List<Layout> layoutList = this.layoutService.findAll();

		for (Layout layout : layoutList)
		{
			if (layout.getIsOptimized() == null || !layout.getIsOptimized())
			{
				byte[] optimizedImage = ImageUtil.optimizeImage(layout.getImagem(),
						layout.getImagemContentType().split("/")[1]);
				layout.setOptimizedImage(optimizedImage);
				layout.setIsOptimized(true);
				this.layoutService.save(layout);
			}
		}

	}

}
