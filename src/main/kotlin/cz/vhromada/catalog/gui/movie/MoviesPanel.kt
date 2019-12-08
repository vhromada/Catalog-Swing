package cz.vhromada.catalog.gui.movie

import cz.vhromada.catalog.entity.Movie
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.facade.MovieFacade
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with movies' data.
 *
 * @author Vladimir Hromada
 */
class MoviesPanel(
        private val movieFacade: MovieFacade,
        private val genreFacade: GenreFacade,
        private val pictureFacade: PictureFacade) : AbstractOverviewDataPanel<Movie>(MoviesListDataModel(movieFacade), MoviesStatsTableDataModel(movieFacade)) {

    override fun getInfoDialog(add: Boolean, data: Movie?): AbstractInfoDialog<Movie> {
        return if (add) MovieInfoDialog(genreFacade, pictureFacade) else MovieInfoDialog(genreFacade, pictureFacade, data!!)
    }

    override fun deleteData() {
        movieFacade.newData()
    }

    override fun addData(data: Movie) {
        movieFacade.add(data)
    }

    override fun updateData(data: Movie) {
        movieFacade.update(data)
    }

    override fun removeData(data: Movie) {
        movieFacade.remove(data)
    }

    override fun duplicatesData(data: Movie) {
        movieFacade.duplicate(data)
    }

    override fun moveUpData(data: Movie) {
        movieFacade.moveUp(data)
    }

    override fun moveDownData(data: Movie) {
        movieFacade.moveDown(data)
    }

    override fun getDataPanel(data: Movie): JPanel {
        return MovieDataPanel(data, pictureFacade)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Movie) {
        // nothing
    }

}
