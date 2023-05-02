/*
 * BannerRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.components;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.Banner;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(a) from Banner a")
	int countBanners();

	@Query("select a from Banner a where :currentDate between a.inicialPeriod and a.finalPeriod")
	List<Banner> findValidBanners(@Param("currentDate") Date currentDate);

	default Banner findRandomBanner() {
		Banner result = null;
		final Date currentDate = MomentHelper.getCurrentMoment();
		final List<Banner> list = this.findValidBanners(currentDate);
		if (!list.isEmpty()) {
			final ThreadLocalRandom random = ThreadLocalRandom.current();
			result = list.get(random.nextInt(list.size()));
		}
		return result;
	}

}
