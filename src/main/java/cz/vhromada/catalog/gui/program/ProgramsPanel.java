package cz.vhromada.catalog.gui.program;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.facade.ProgramFacade;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel;

/**
 * A class represents panel with programs' data.
 *
 * @author Vladimir Hromada
 */
public class ProgramsPanel extends AbstractOverviewDataPanel<Program> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for programs
     */
    private final ProgramFacade programFacade;

    /**
     * Creates a new instance of ProgramsPanel.
     *
     * @param programFacade facade for programs
     * @throws IllegalArgumentException if facade for programs is null
     */
    public ProgramsPanel(final ProgramFacade programFacade) {
        super(getProgramsListDataModel(programFacade), getProgramsStatsTableDataModel(programFacade));

        this.programFacade = programFacade;
    }

    @Override
    protected AbstractInfoDialog<Program> getInfoDialog(final boolean add, final Program data) {
        return add ? new ProgramInfoDialog() : new ProgramInfoDialog(data);
    }

    @Override
    protected void deleteData() {
        programFacade.newData();
    }

    @Override
    protected void addData(final Program data) {
        programFacade.add(data);
    }

    @Override
    protected void updateData(final Program data) {
        programFacade.update(data);
    }

    @Override
    protected void removeData(final Program data) {
        programFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Program data) {
        programFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Program data) {
        programFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Program data) {
        programFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Program data) {
        return new ProgramDataPanel(data);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Program data) {
    }

    /**
     * Returns data model for list with programs.
     *
     * @param facade facade for programs
     * @return data model for list with programs
     * @throws IllegalArgumentException if facade for programs is null
     */
    private static ProgramsListDataModel getProgramsListDataModel(final ProgramFacade facade) {
        return new ProgramsListDataModel(facade);
    }

    /**
     * Returns data model for table with stats for programs.
     *
     * @param facade facade for programs
     * @return data model for table with stats for programs
     * @throws IllegalArgumentException if facade for programs is null
     */
    private static ProgramsStatsTableDataModel getProgramsStatsTableDataModel(final ProgramFacade facade) {
        return new ProgramsStatsTableDataModel(facade);
    }

}
