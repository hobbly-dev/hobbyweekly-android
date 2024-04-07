package kr.hobbly.hobbyweekly.android.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kr.hobbly.hobbyweekly.android.data.repository.feature.community.MockCommunityRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication.MockAuthenticationRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication.token.MockTokenRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.file.MockFileRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.tracking.MockTrackingRepository
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user.MockUserRepository
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository
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
        authenticationRepository: MockAuthenticationRepository
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindsFileRepository(
        fileRepository: MockFileRepository
    ): FileRepository

    @Binds
    @Singleton
    abstract fun bindsTokenRepository(
        tokenRepository: MockTokenRepository
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(
        userRepository: MockUserRepository
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindsTrackingRepository(
        userRepository: MockTrackingRepository
    ): TrackingRepository

    @Binds
    @Singleton
    abstract fun bindsCommunityRepository(
        communityRepository: MockCommunityRepository
    ): CommunityRepository
}
