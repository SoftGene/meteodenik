package cz.utb.meteodenik.data

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> bezpecneVolani(blok: suspend () -> T): VysledekSite<T> {
    return try {
        VysledekSite.Uspech(blok())
    } catch (e: CancellationException) {
        throw e
    } catch (e: UnknownHostException) {
        VysledekSite.Chyba(DuvodSelhani.BEZ_INTERNETU)
    } catch (e: SocketTimeoutException) {
        VysledekSite.Chyba(DuvodSelhani.TIMEOUT)
    } catch (e: HttpException) {
        VysledekSite.Chyba(DuvodSelhani.SERVER)
    } catch (e: SerializationException) {
        VysledekSite.Chyba(DuvodSelhani.CHYBA_DAT)
    } catch (e: IOException) {
        VysledekSite.Chyba(DuvodSelhani.BEZ_INTERNETU)
    } catch (e: Exception) {
        VysledekSite.Chyba(DuvodSelhani.NEZNAMA)
    }
}