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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(a) from Banner a")
	int countBanners();

	@Query("select a from Banner a")
	List<Banner> findManyBanners(PageRequest pageRequest);

	default Banner findRandomBanner() {
		Banner result = new Banner();
		int count, index;
		ThreadLocalRandom random;
		PageRequest page;
		List<Banner> list;

		count = this.countBanners();
		if (count == 0)
			result = null;
		else
			while (!result.isActive() || result != null) {
				random = ThreadLocalRandom.current();
				index = random.nextInt(0, count);

				page = PageRequest.of(index, 1);
				list = this.findManyBanners(page);
				result = list.isEmpty() ? null : list.get(0);
			}

		return result;
	}

}
