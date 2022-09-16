import {FlinkClusterConfig, FlinkClusterConfigParam} from "@/services/dev/typings";
import {request} from "@@/exports";
import {PageResponse, ResponseBody} from "@/app.d";
import {FormInstance} from "antd/lib/form/hooks/useForm";

const url: string = '/api/flink/cluster-config';

export async function list(queryParam: FlinkClusterConfigParam) {
  return request<PageResponse<FlinkClusterConfig>>(`${url}`, {
    method: 'GET',
    params: queryParam
  }).then((res) => {
    const result = {
      data: res.records,
      total: res.total,
      pageSize: res.size,
      current: res.current,
    };
    return result;
  })
}

export async function selectOne(id: number) {
  return request<FlinkClusterConfig>(`${url}/` + id, {
    method: 'GET'
  });
}

export async function add(row: FlinkClusterConfig) {
  return request<ResponseBody<any>>(`${url}`, {
    method: 'PUT',
    data: row,
  });
}

export async function update(row: FlinkClusterConfig) {
  return request<ResponseBody<any>>(`${url}/` + row.id, {
    method: 'POST',
    data: row,
  });
}

export async function deleteOne(row: FlinkClusterConfig) {
  return request<ResponseBody<any>>(`${url}/` + row.id, {
    method: 'DELETE'
  })
}

export async function deleteBatch(rows: FlinkClusterConfig[]) {
  const params = rows.map((row) => row.id);
  return request<ResponseBody<any>>(`${url}/batch`, {
    method: 'DELETE',
    data: params
  })
}

export function getData(value: Record<string, any>) {
  const options = new Map<string, any>();
  options['state.backend'] = value['state.backend']
  options['state.savepoints.dir'] = value['state.savepoints.dir']
  options['state.checkpoints.dir'] = value['state.checkpoints.dir']
  options['execution.checkpointing.mode'] = value['execution.checkpointing.mode']
  options['execution.checkpointing.unaligned'] = value['execution.checkpointing.unaligned']
  options['execution checkpointing interval'] = value['execution checkpointing interval']
  options['execution.checkpointing.externalized-checkpoint-retention'] = value['execution.checkpointing.externalized-checkpoint-retention']

  options['restart-strategy'] = value['strategy']
  options['restart-strategy.fixed-delay.attempts'] = value['restart-strategy.fixed-delay.attempts']
  options['restart-strategy.fixed-delay.delay'] = value['restart-strategy.fixed-delay.delay']
  options['restart-strategy.failure-rate.delay'] = value['restart-strategy.failure-rate.delay']
  options['restart-strategy.failure-rate.failure-rate-interval'] = value['restart-strategy.failure-rate.failure-rate-interval']
  options['restart-strategy.failure-rate.max-failures-per-interval'] = value['restart-strategy.failure-rate.max-failures-per-interval']
  options['restart-strategy.exponential-delay.initial-backoff'] = value['restart-strategy.exponential-delay.initial-backoff']
  options['restart-strategy.exponential-delay.backoff-multiplier'] = value['restart-strategy.exponential-delay.backoff-multiplier']
  options['restart-strategy.exponential-delay.max-backoff'] = value['restart-strategy.exponential-delay.max-backoff']
  options['restart-strategy.exponential-delay.reset-backoff-threshold'] = value['restart-strategy.exponential-delay.reset-backoff-threshold']
  options['restart-strategy.exponential-delay.jitter-factor'] = value['restart-strategy.exponential-delay.jitter-factor']

  options['high-availability'] = value['ha']
  options['high-availability.storageDir'] = value['high-availability.storageDir']
  options['high-availability.cluster-id'] = value['high-availability.cluster-id']
  options['high-availability.zookeeper.path.root'] = value['high-availability.zookeeper.path.root']
  options['high-availability.zookeeper.quorum'] = value['high-availability.zookeeper.quorum']

  options['jobmanager.memory.process.size'] = value['jobmanager.memory.process.size']
  options['jobmanager.memory.flink.size'] = value['jobmanager.memory.flink.size']
  options['taskmanager.memory.process.size'] = value['taskmanager.memory.process.size']
  options['taskmanager.memory.flink.size'] = value['taskmanager.memory.flink.size']

  value.options?.forEach(function (item: Record<string, any>) {
    options[item.key] = item.value
  })
  return options;
}

export async function setData(form: FormInstance, configOptions: { [key: string]: any }) {
  const data = {
    'state.backend': configOptions.get('state.backend'),
    'state.savepoints.dir': configOptions.get('state.savepoints.dir'),
    'state.checkpoints.dir': configOptions.get('state.checkpoints.dir'),
    'execution.checkpointing.mode': configOptions.get('execution.checkpointing.mode'),
    'execution.checkpointing.unaligned': configOptions.get('execution.checkpointing.unaligned'),
    'execution checkpointing interval': configOptions.get('execution checkpointing interval'),
    'execution.checkpointing.externalized-checkpoint-retention': configOptions.get('execution.checkpointing.externalized-checkpoint-retention'),

    'strategy': configOptions.get('restart-strategy'),
    'restart-strategy.fixed-delay.attempts': configOptions.get('restart-strategy.fixed-delay.attempts'),
    'restart-strategy.fixed-delay.delay': configOptions.get('restart-strategy.fixed-delay.delay'),
    'restart-strategy.failure-rate.delay': configOptions.get('restart-strategy.failure-rate.delay'),
    'restart-strategy.failure-rate.failure-rate-interval': configOptions.get('restart-strategy.failure-rate.failure-rate-interval'),
    'restart-strategy.failure-rate.max-failures-per-interval': configOptions.get('restart-strategy.failure-rate.max-failures-per-interval'),
    'restart-strategy.exponential-delay.initial-backoff': configOptions.get('restart-strategy.exponential-delay.initial-backoff'),
    'restart-strategy.exponential-delay.backoff-multiplier': configOptions.get('restart-strategy.exponential-delay.backoff-multiplier'),
    'restart-strategy.exponential-delay.max-backoff': configOptions.get('restart-strategy.exponential-delay.max-backoff'),
    'restart-strategy.exponential-delay.reset-backoff-threshold': configOptions.get('restart-strategy.exponential-delay.reset-backoff-threshold'),
    'restart-strategy.exponential-delay.jitter-factor': configOptions.get('restart-strategy.exponential-delay.jitter-factor'),

    'ha': configOptions.get('high-availability'),
    'high-availability.storageDir': configOptions.get('high-availability.storageDir'),
    'high-availability.cluster-id': configOptions.get('high-availability.cluster-id'),
    'high-availability.zookeeper.path.root': configOptions.get('high-availability.zookeeper.path.root'),
    'high-availability.zookeeper.quorum': configOptions.get('high-availability.zookeeper.quorum'),

    'jobmanager.memory.process.size': configOptions.get('jobmanager.memory.process.size'),
    'jobmanager.memory.flink.size': configOptions.get('jobmanager.memory.flink.size'),
    'taskmanager.memory.process.size': configOptions.get('taskmanager.memory.process.size'),
    'taskmanager.memory.flink.size': configOptions.get('taskmanager.memory.flink.size'),
  }

  configOptions.delete('state.backend')
  configOptions.delete('state.savepoints.dir')
  configOptions.delete('state.checkpoints.dir')
  configOptions.delete('execution.checkpointing.mode')
  configOptions.delete('execution.checkpointing.unaligned')
  configOptions.delete('execution checkpointing interval')
  configOptions.delete('execution.checkpointing.externalized-checkpoint-retention')

  configOptions.delete('restart-strategy')
  configOptions.delete('restart-strategy.fixed-delay.attempts')
  configOptions.delete('restart-strategy.fixed-delay.delay')
  configOptions.delete('restart-strategy.failure-rate.delay')
  configOptions.delete('restart-strategy.failure-rate.failure-rate-interval')
  configOptions.delete('restart-strategy.failure-rate.max-failures-per-interval')
  configOptions.delete('restart-strategy.exponential-delay.initial-backoff')
  configOptions.delete('restart-strategy.exponential-delay.backoff-multiplier')
  configOptions.delete('restart-strategy.exponential-delay.max-backoff')
  configOptions.delete('restart-strategy.exponential-delay.reset-backoff-threshold')
  configOptions.delete('restart-strategy.exponential-delay.jitter-factor')

  configOptions.delete('high-availability')
  configOptions.delete('high-availability.storageDir')
  configOptions.delete('high-availability.cluster-id')
  configOptions.delete('high-availability.zookeeper.path.root')
  configOptions.delete('high-availability.zookeeper.quorum')

  configOptions.delete('jobmanager.memory.process.size')
  configOptions.delete('jobmanager.memory.flink.size')
  configOptions.delete('taskmanager.memory.process.size')
  configOptions.delete('taskmanager.memory.flink.size')

  const options: Array<any> = []
  configOptions.forEach((value: any, key: string) => {
    options.push({key: key, value: value})
  })
  data['options'] = options
  form.setFieldsValue(data)
}