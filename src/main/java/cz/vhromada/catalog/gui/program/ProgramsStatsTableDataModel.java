package cz.vhromada.catalog.gui.program;

import java.util.List;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.facade.ProgramFacade;
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel;
import cz.vhromada.result.Result;
import cz.vhromada.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents data model for table with stats for programs.
 *
 * @author Vladimir Hromada
 */
public class ProgramsStatsTableDataModel extends AbstractStatsTableDataModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for programs
     */
    private final ProgramFacade programFacade;

    /**
     * List of programs
     */
    private List<Program> programs;

    /**
     * Total count of media
     */
    private int totalMediaCount;

    /**
     * Creates a new instance of ProgramsStatsTableDataModel.
     *
     * @param programFacade facade for programs
     * @throws IllegalArgumentException if facade for programs is null
     */
    public ProgramsStatsTableDataModel(final ProgramFacade programFacade) {
        Assert.notNull(programFacade, "Facade for programs mustn't be null.");

        this.programFacade = programFacade;
        update();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return programs.size();
            case 1:
                return totalMediaCount;
            default:
                throw new IndexOutOfBoundsException("Bad column");
        }
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return Integer.class;
    }

    @Override
    public String getColumnName(final int column) {
        switch (column) {
            case 0:
                return "Count of programs";
            case 1:
                return "Count of media";
            default:
                throw new IndexOutOfBoundsException("Bad column");
        }
    }

    @Override
    public final void update() {
        final Result<List<Program>> programsResult = programFacade.getAll();
        final Result<Integer> totalMediaCountResult = programFacade.getTotalMediaCount();

        final Result<Void> result = new Result<>();
        result.addEvents(programsResult.getEvents());
        result.addEvents(totalMediaCountResult.getEvents());

        if (Status.OK == result.getStatus()) {
            programs = programsResult.getData();
            totalMediaCount = totalMediaCountResult.getData();
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

}
