package cz.vhromada.catalog.gui.movie

import cz.vhromada.catalog.entity.Movie
import cz.vhromada.catalog.facade.MovieFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.common.result.Result

/**
 * A class represents data model for list with movies.
 *
 * @author Vladimir Hromada
 */
class MoviesListDataModel(private val movieFacade: MovieFacade) : AbstractListDataModel<Movie>() {

    init {
        update()
    }

    override fun getData(): Result<List<Movie>> {
        return movieFacade.getAll()
    }

    override fun getDisplayValue(dataObject: Movie): String {
        return dataObject.czechName!!
    }

}
