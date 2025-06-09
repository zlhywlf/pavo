package zlhywlf.utils

import org.gradle.api.provider.Provider

object ProviderUtil {
    internal fun <A, B> zip(
        aProvider: Provider<A>,
        bProvider: Provider<B>,
    ): Provider<Pair<A, B>> {
        return aProvider.flatMap { a -> bProvider.map { b -> Pair(a, b) } }
    }

    internal fun <A, B, C> zip(
        aProvider: Provider<A>,
        bProvider: Provider<B>,
        cProvider: Provider<C>,
    ):
        Provider<Triple<A, B, C>> {
        return zip(aProvider, bProvider).flatMap { pair -> cProvider.map { c -> Triple(pair.first, pair.second, c) } }
    }
}
