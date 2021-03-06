package com.kavrin.marvin.domain.use_cases.splash_welcome

import com.kavrin.marvin.data.repository.Repository

class SaveOnBoarding(
	private val repository: Repository
) {

	suspend operator fun invoke(completed: Boolean) {
		repository.saveOnBoardingState(completed = completed)
	}
}