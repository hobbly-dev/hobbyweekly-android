package kr.hobbly.hobbyweekly.android.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.RealCommunityRepository
import kr.hobbly.hobbyweekly.android.data.repository.feature.routine.RealRoutineRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication.RealAuthenticationRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication.token.RealTokenRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.file.RealFileRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.tracking.RealTrackingRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user.RealUserRepository
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.FileRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TrackingRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindsAuthenticationRepository(
        authenticationRepository: RealAuthenticationRepository
    ): AuthenticationRepository

    @Binds
    @Singleton
    internal abstract fun bindsFileRepository(
        fileRepository: RealFileRepository
    ): FileRepository

    @Binds
    @Singleton
    internal abstract fun bindsTokenRepository(
        tokenRepository: RealTokenRepository
    ): TokenRepository

    @Binds
    @Singleton
    internal abstract fun bindsUserRepository(
        userRepository: RealUserRepository
    ): UserRepository

    @Binds
    @Singleton
    internal abstract fun bindsTrackingRepository(
        userRepository: RealTrackingRepository
    ): TrackingRepository

    @Binds
    @Singleton
    internal abstract fun bindsCommunityRepository(
        communityRepository: RealCommunityRepository
    ): CommunityRepository

    @Binds
    @Singleton
    internal abstract fun bindsRoutineRepository(
        routineRepository: RealRoutineRepository
    ): RoutineRepository
}
