package cz.vhromada.catalog.gui.episode

import cz.vhromada.catalog.entity.Episode
import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.facade.EpisodeFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.common.result.Result

/**
 * A class represents data model for list with episodes.
 *
 * @author Vladimir Hromada
 */
class EpisodesListDataModel(
        private val episodeFacade: EpisodeFacade,
        private val season: Season) : AbstractListDataModel<Episode>() {

    init {
        update()
    }

    override fun getData(): Result<List<Episode>> {
        return episodeFacade.find(season)
    }

    override fun getDisplayValue(dataObject: Episode): String {
        return dataObject.name!!
    }

}
