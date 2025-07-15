import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { lighten } from '@mui/system';
import {
  Table, TableBody, TableCell, TableContainer, TableHead,
  TablePagination, TableRow, TableSortLabel, Toolbar,
  Typography, Paper, Checkbox, IconButton, Tooltip,
  FormControlLabel, Switch
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import FilterListIcon from '@mui/icons-material/FilterList';

import styles from './compCadastroAluno.module.css';
import CadastroAluno from '../CadastroAluno/CadastroAluno';

function createData(nome, matricula, email, turma, acoes) {
  return { nome, matricula, email, turma, acoes };
}

const rows = [
  createData('Maria Lucia Alves', 254, 'maria@email.com', 67, 4.3),
  createData('João Carvalho', 241, 'joao@email.com', 51, 4.9),
  createData('Clara Silva', 286, 'clara@email.com', 24, 6.0),
  createData('Alberto Roberto', 253, 'alberto@email.com', 24, 4.0),
  createData('Martina Eclair', 386, 'martina@email.com', 49, 3.9),
  createData('Jorge Egrégio', 345, 'jorge@email.com', 87, 6.5),
  createData('Rita Lee Jones', 289, 'rita@email.com', 37, 4.3),
  createData('Francisco Ferdinando', 297, 'francisco@email.com', 94, 0.0),
  createData('Carlito Coral', 432, 'carlito@email.com', 65, 7.0),
  createData('Patrícia Joana', 324, 'patricia@email.com', 98, 0.0),
  createData('Maurício Dimas', 423, 'mauricio@email.com', 81, 2.0),
  createData('Nougar Nimódeo', 561, 'nougar@email.com', 9, 37.0),
  createData('Oreostides Mileas', 461, 'oreostides@email.com', 63, 4.0),
];

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) return -1;
  if (b[orderBy] > a[orderBy]) return 1;
  return 0;
}

function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort(array, comparator) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map((el) => el[0]);
}

const headCells = [
  { id: 'nome', numeric: false, disablePadding: true, label: 'Nome' },
  { id: 'matricula', numeric: true, disablePadding: false, label: 'Código da Matrícula' },
  { id: 'email', numeric: false, disablePadding: true, label: 'E-mail' },
  { id: 'turma', numeric: true, disablePadding: false, label: 'Turma' },
  { id: 'acoes', numeric: true, disablePadding: false, label: 'Ações' },
];

function EnhancedTableHead(props) {
  const { onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort } = props;
  const createSortHandler = (property) => (event) => onRequestSort(event, property);

  return (
    <TableHead>
      <TableRow>
        <TableCell padding="checkbox">
          <Checkbox
            indeterminate={numSelected > 0 && numSelected < rowCount}
            checked={rowCount > 0 && numSelected === rowCount}
            onChange={onSelectAllClick}
            inputProps={{ 'aria-label': 'select all' }}
          />
        </TableCell>
        {headCells.map((headCell) => (
          <TableCell
            key={headCell.id}
            align={headCell.numeric ? 'right' : 'left'}
            padding={headCell.disablePadding ? 'none' : 'normal'}
            sortDirection={orderBy === headCell.id ? order : false}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : 'asc'}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <span style={{
                  border: 0,
                  clip: 'rect(0 0 0 0)',
                  height: 1,
                  margin: -1,
                  overflow: 'hidden',
                  padding: 0,
                  position: 'absolute',
                  top: 20,
                  width: 1,
                }}>
                  {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                </span>
              ) : null}
            </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

EnhancedTableHead.propTypes = {
  numSelected: PropTypes.number.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  onSelectAllClick: PropTypes.func.isRequired,
  order: PropTypes.oneOf(['asc', 'desc']).isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};

const EnhancedTableToolbar = ({ numSelected }) => (
  <Toolbar
    sx={(theme) => ({
      pl: 2,
      pr: 1,
      ...(numSelected > 0 && {
        color: theme.palette.secondary.main,
        backgroundColor: lighten(theme.palette.secondary.light, 0.85),
      }),
    })}
  >
    {numSelected > 0 ? (
      <Typography sx={{ flex: '1 1 100%' }} color="inherit" variant="subtitle1" component="div">
        {numSelected} selecionado(s)
      </Typography>
    ) : (
      <Typography sx={{ flex: '1 1 100%' }} variant="h6" id="tableTitle" component="div">
        Alunos
      </Typography>
    )}
    {numSelected > 0 ? (
      <Tooltip title="Deletar">
        <IconButton aria-label="delete">
          <DeleteIcon />
        </IconButton>
      </Tooltip>
    ) : (
      <Tooltip title="Filtrar lista">
        <IconButton aria-label="filter list">
          <FilterListIcon />
        </IconButton>
      </Tooltip>
    )}
  </Toolbar>
);

EnhancedTableToolbar.propTypes = {
  numSelected: PropTypes.number.isRequired,
};

export default function ModuloCadastroAluno() {
  const [order, setOrder] = useState('asc');
  const [orderBy, setOrderBy] = useState('nome');
  const [selected, setSelected] = useState([]);
  const [page, setPage] = useState(0);
  const [dense, setDense] = useState(false);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelecteds = rows.map((n) => n.nome);
      setSelected(newSelecteds);
      return;
    }
    setSelected([]);
  };

  const handleClick = (event, name) => {
    const selectedIndex = selected.indexOf(name);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = [...selected, name];
    } else if (selectedIndex === 0) {
      newSelected = selected.slice(1);
    } else if (selectedIndex === selected.length - 1) {
      newSelected = selected.slice(0, -1);
    } else if (selectedIndex > 0) {
      newSelected = [...selected.slice(0, selectedIndex), ...selected.slice(selectedIndex + 1)];
    }

    setSelected(newSelected);
  };

  const handleChangePage = (event, newPage) => setPage(newPage);
  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };
  const handleChangeDense = (event) => setDense(event.target.checked);

  const isSelected = (name) => selected.indexOf(name) !== -1;

  const emptyRows = rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage);

  const [modalAlunoAberto, setModalAlunoAberto] = useState(false);
  const abrirModalAluno = () => setModalAlunoAberto(true);
  const fecharModalAluno = () => setModalAlunoAberto(false);

  return (
    <div style={{ width: '100%' }}>
      <Paper sx={{ width: '100%', mb: 2 }}>
        <EnhancedTableToolbar numSelected={selected.length} />
        <TableContainer>
          <Table sx={{ minWidth: 750 }} size={dense ? 'small' : 'medium'}>
            <EnhancedTableHead
              numSelected={selected.length}
              order={order}
              orderBy={orderBy}
              onSelectAllClick={handleSelectAllClick}
              onRequestSort={handleRequestSort}
              rowCount={rows.length}
            />
            <TableBody>
              {stableSort(rows, getComparator(order, orderBy))
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row, index) => {
                  const isItemSelected = isSelected(row.nome);
                  const labelId = `enhanced-table-checkbox-${index}`;

                  return (
                    <TableRow
                      hover
                      onClick={(event) => handleClick(event, row.nome)}
                      role="checkbox"
                      aria-checked={isItemSelected}
                      tabIndex={-1}
                      key={row.nome}
                      selected={isItemSelected}
                    >
                      <TableCell padding="checkbox">
                        <Checkbox
                          checked={isItemSelected}
                          inputProps={{ 'aria-labelledby': labelId }}
                        />
                      </TableCell>
                      <TableCell component="th" id={labelId} scope="row" padding="none">
                        {row.nome}
                      </TableCell>
                      <TableCell align="right">{row.matricula}</TableCell>
                      <TableCell align="right">{row.email}</TableCell>
                      <TableCell align="right">{row.turma}</TableCell>
                      <TableCell align="right">{row.acoes}</TableCell>
                    </TableRow>
                  );
                })}
              {emptyRows > 0 && (
                <TableRow style={{ height: (dense ? 33 : 53) * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={rows.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
      <FormControlLabel
        control={<Switch checked={dense} onChange={handleChangeDense} />}
        label="Dense padding"
      />
      <div className={styles.botoes}>
        <button className={styles.botaoCadastrar} onClick={abrirModalAluno}>
          Cadastrar Aluno
        </button>
      </div>
      <CadastroAluno isOpen={modalAlunoAberto} onClose={fecharModalAluno} />
    </div>
  );
}
