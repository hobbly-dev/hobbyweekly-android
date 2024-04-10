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
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsAuthenticationRepository(
        authenticationRepository: RealAuthenticationRepository
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindsFileRepository(
        fileRepository: RealFileRepository
    ): FileRepository

    @Binds
    @Singleton
    abstract fun bindsTokenRepository(
        tokenRepository: RealTokenRepository
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(
        userRepository: RealUserRepository
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindsTrackingRepository(
        userRepository: RealTrackingRepository
    ): TrackingRepository

    @Binds
    @Singleton
    abstract fun bindsCommunityRepository(
        communityRepository: RealCommunityRepository
    ): CommunityRepository

    @Binds
    @Singleton
    abstract fun bindsRoutineRepository(
        routineRepository: RealRoutineRepository
    ): RoutineRepository
}
