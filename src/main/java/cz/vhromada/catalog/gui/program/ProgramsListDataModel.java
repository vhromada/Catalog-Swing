package cz.vhromada.catalog.gui.program;

import java.util.List;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.facade.ProgramFacade;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with programs.
 *
 * @author Vladimir Hromada
 */
public class ProgramsListDataModel extends AbstractListDataModel<Program> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for programs
     */
    private ProgramFacade programFacade;

    /**
     * Creates a new instance of ProgramsListDataModel.
     *
     * @param programFacade facade for programs
     * @throws IllegalArgumentException if facade for programs is null
     */
    public ProgramsListDataModel(final ProgramFacade programFacade) {
        Assert.notNull(programFacade, "Facade for programs mustn't be null");

        this.programFacade = programFacade;
        update();
    }

    @Override
    protected Result<List<Program>> getData() {
        return programFacade.getAll();
    }

    @Override
    protected String getDisplayValue(final Program dataObject) {
        return dataObject.getName();
    }

}
