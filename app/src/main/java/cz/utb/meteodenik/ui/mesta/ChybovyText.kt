package cz.utb.meteodenik.ui.mesta

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.utb.meteodenik.R
import cz.utb.meteodenik.data.DuvodSelhani

@Composable
fun chybovyText(duvod: DuvodSelhani): String = when (duvod) {
    DuvodSelhani.BEZ_INTERNETU -> stringResource(R.string.chyba_bez_internetu)
    DuvodSelhani.TIMEOUT -> stringResource(R.string.chyba_timeout)
    DuvodSelhani.SERVER -> stringResource(R.string.chyba_server)
    DuvodSelhani.CHYBA_DAT -> stringResource(R.string.chyba_dat)
    DuvodSelhani.NEZNAMA -> stringResource(R.string.chyba_neznama)
}